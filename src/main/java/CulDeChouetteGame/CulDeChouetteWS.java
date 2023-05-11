/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import static javax.json.Json.createReader;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Arnaud
 */
@ServerEndpoint("/CulDeChouetteWS")
public class CulDeChouetteWS {
    
    private static String actualPlayer = null;
    private static ConcurrentHashMap<String, Session> players = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> scoreboard = new ConcurrentHashMap<>();
    private static ArrayList<String> playerOrder = null;
    private static ArrayList<Integer> dices = new ArrayList<>();
    private static ArrayList<String> interaction = new ArrayList<>();
    
    static {
        playerOrder = Game.playerOrder;
        if(!playerOrder.isEmpty()) {
            actualPlayer = playerOrder.get(0);            
        } else {
            System.out.println("Erreur, tableau non initilaisé!");
        }
        
    }
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig EndpointConfig) throws IOException {
        System.out.println("Taille ArrayList playerOrder: " + playerOrder.size());
        System.out.println ("WebSocket ouverte sur CulDeChouetteWS : "+session.getId());
        JsonObject openMessage = Json.createObjectBuilder()
                .add("message", "Connecté avec succès à Java sur le serveur CulDeChouetteWS")
                .build();
        String messageString = openMessage.toString();
        session.getBasicRemote().sendText(messageString);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Message recu: " + message);
        JsonReader jsonReader = createReader(new StringReader(message)); // Transformation du message en JSON
        JsonObject jsonObject = jsonReader.readObject();
        if(jsonObject.containsKey("username")) {
            String username = jsonObject.getString("username");
            System.out.println(username);
            players.put(username, session);
            scoreboard.put(username, 0);
            JsonObject firstUserToPlay = Json.createObjectBuilder()
                .add("type", "actualPlayer")
                .add("player", actualPlayer)
                .build();
            String messageString = firstUserToPlay.toString();
            session.getBasicRemote().sendText(messageString);
            updateScoreboard();
        }
        if(jsonObject.containsKey("message")) {
            switch(jsonObject.getString("message")) {
                case "rollDiceChouette":
                    Game.lastPlayerResults.clear();
                    int dice1 = Game.rollDice();
                    int dice2 = Game.rollDice();
                    JsonObject diceChouetteResult = Json.createObjectBuilder()
                            .add("type", "diceChouetteResult")
                            .add("dice1", dice1)
                            .add("dice2", dice2)
                            .build();
                    String serialJsonChouetteResult = diceChouetteResult.toString();
                    for(Session player: players.values()) {
                        player.getBasicRemote().sendText(serialJsonChouetteResult);
                    }
                    break;
                case "rollDiceCul":
                    int dice3 = Game.rollDice();
                    JsonObject diceCulResult = Json.createObjectBuilder()
                            .add("type", "diceCulResult")
                            .add("dice", dice3)
                            .build();
                    String serialJsonCulResult = diceCulResult.toString();
                    for(Session player: players.values()) {
                        player.getBasicRemote().sendText(serialJsonCulResult);
                    }
                    if(Game.detectInteraction()) {
                        if(Game.areConsecutives) {
                            // TODO envoyer message client attente interraction
                            JsonObject activateSuite = Json.createObjectBuilder()
                            .add("type", "activateSuiteButton")
                            .build();
                            String activateSuiteStr = activateSuite.toString();
                            for(Session player: players.values()) {
                                player.getBasicRemote().sendText(activateSuiteStr);
                            }
                            Game.areConsecutives = false;
                        }
                        if(Game.areChouetteVelute) {
                            JsonObject activateCv = Json.createObjectBuilder()
                            .add("type", "activateChouetteVelute")
                            .build();
                            String activateCvStr = activateCv.toString();
                            for(Session player: players.values()) {
                                player.getBasicRemote().sendText(activateCvStr);
                            }
                            Game.areChouetteVelute = false;
                        }
                    } else {
                        updatePlayerScore();
                        nextPlayer();
                    }
                    break;
                case "interactionSuite":
                    String playerSuite = jsonObject.getString("player");
                    interaction.add(playerSuite);
                    if(interaction.size() == playerOrder.size()) {
                        System.out.println("All players have responded");
                        String playerLost = interaction.get(interaction.size() - 1); // Selection du perdant
                        Game.setSuiteLoose(playerLost);
                        playerLostSuite(playerLost);
                        interaction.clear();
                        Game.lastPlayerResults.clear();
                        nextPlayer();
                    }
                    break;
                case "interactionChouetteVelute":
                    String playerCv = jsonObject.getString("player");
                    interaction.add(playerCv);
                    if(interaction.size() == playerOrder.size()) {
                        System.out.println("All players have responded");
                        String playerWin = interaction.get(0); // Selection du gagnant
                        Game.setChouetteVelueWinner(playerWin);
                        playerWinCv(playerWin);
                        interaction.clear();
                        Game.lastPlayerResults.clear();
                        nextPlayer();
                    }
                    break;
            }
        }    
        jsonReader.close();
    }

    @OnClose
    public void onClose(Session session, CloseReason close_reason) throws IOException {
        Iterator<Map.Entry<String, Session>> iterator = players.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Session> entry = iterator.next();
            if(entry.getValue().equals(session)) {
                iterator.remove();
            }
        }
        if(players.isEmpty()) {
            LobbyWS.resetUserList();
            Game.clearGameData();
        }
        System.out.println("onClose: " + close_reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
    
    private void nextPlayer() throws IOException {
        int i = playerOrder.lastIndexOf(actualPlayer);
        System.out.println("Last play pos: " + i);
        actualPlayer = playerOrder.get((i + 1) % playerOrder.size());
        JsonObject firstUserToPlay = Json.createObjectBuilder()
            .add("type", "actualPlayer")
            .add("player", actualPlayer)
            .build();
        String nextPlayerStr = firstUserToPlay.toString();
        for(Session player : players.values()) {
            player.getBasicRemote().sendText(nextPlayerStr);
        }
    }
    
    private void playerWinCv(String player) throws IOException {
        int oldScore = scoreboard.get(player);
        int score = Game.calculateScore();
        scoreboard.put(player, oldScore + score);
        updateScoreboard();
    }
    
    private void playerLostSuite(String player) throws IOException {
        int oldScore = scoreboard.get(player);
        scoreboard.put(player, oldScore - 10);
        updateScoreboard();
    }
    
    private void updatePlayerScore() throws IOException {
        int score = Game.calculateScore();
        int oldScore = scoreboard.get(actualPlayer);
        scoreboard.put(actualPlayer, oldScore + score);
        updateScoreboard();
    }
    
    private void updateScoreboard() throws IOException {
        JsonArrayBuilder playersScoreArrayBuilder = Json.createArrayBuilder();
        for(Map.Entry<String, Integer> entry : scoreboard.entrySet()) {
            String player = entry.getKey();
            int score = entry.getValue();

            JsonObjectBuilder playerScoreBuilder = Json.createObjectBuilder()
                    .add(player, score);

            playersScoreArrayBuilder.add(playerScoreBuilder);
        }
        JsonObject scoreboardJson = Json.createObjectBuilder()
                .add("type", "scoreboard")
                .add("playersScore", playersScoreArrayBuilder)
                .build();
        for(Session player : players.values()) {
            player.getBasicRemote().sendText(scoreboardJson.toString());
        }
    }
    
    public static ArrayList<String> initUserOrder() {
        return LobbyWS.getUserList();
    }
}
