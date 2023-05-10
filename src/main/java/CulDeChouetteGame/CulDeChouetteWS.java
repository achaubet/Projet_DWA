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
    
    static {
        playerOrder = Game.playerOrder;
        if(playerOrder.size() != 0) {
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
                case "rollDice":
                    int dice1 = Game.rollDice();
                    int dice2 = Game.rollDice();
                    
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
        }
        System.out.println("onClose: " + close_reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
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
