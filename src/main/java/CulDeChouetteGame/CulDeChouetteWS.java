/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import CulDeChouetteDAO.AbstractDAOFactory;
import CulDeChouetteDAO.CulDeChouetteDAOFactory;
import CulDeChouetteDAO.DAOException;
import CulDeChouetteDAO.IJoueur;
import CulDeChouetteDAO.IJoueursPartie;
import CulDeChouetteDAO.IPartie;
import CulDeChouetteDAO.PersistenceKind;
import POJO.Joueur;
import POJO.JoueursPartie;
import POJO.Partie;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final ConcurrentHashMap<String, Session> players = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> scoreboard = new ConcurrentHashMap<>();
    private static ArrayList<String> playerOrder = null;
    private static final ArrayList<String> interaction = new ArrayList<>();
    private static final ArrayList<Session> spectators = new ArrayList<>();
    
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
    public void onMessage(String message, Session session) throws IOException, DAOException {
        System.out.println("Message recu: " + message);
        JsonReader jsonReader = createReader(new StringReader(message)); // Transformation du message en JSON
        JsonObject jsonObject = jsonReader.readObject();
        if(jsonObject.containsKey("username")) {
            String username = jsonObject.getString("username");
            System.out.println("Player: " + username + " has joined the party !");
            JsonObject firstUserToPlay = Json.createObjectBuilder()
                .add("type", "actualPlayer")
                .add("player", actualPlayer)
                .build();
            if(playerOrder.contains(username)) {
                players.put(username, session);
                scoreboard.put(username, 0);
                String messageString = firstUserToPlay.toString();
                session.getBasicRemote().sendText(messageString);
                updateScoreboard();
            } else {
                spectators.add(session);
                JsonObject specMode = Json.createObjectBuilder()
                            .add("type", "spectatorMode")
                            .build();
                String specModeStr = specMode.toString();
                session.getBasicRemote().sendText(specModeStr);
                updateScoreboard();
            }
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
                    spectators.forEach((spectator) -> {
                        try {
                            spectator.getBasicRemote().sendText(serialJsonChouetteResult);
                        } catch (IOException ex) {
                            Logger.getLogger(CulDeChouetteWS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
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
                    spectators.forEach((spectator) -> {
                        try {
                            spectator.getBasicRemote().sendText(serialJsonCulResult);
                        } catch (IOException ex) {
                            Logger.getLogger(CulDeChouetteWS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
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
                // Un utilisateur s'en va, on déconnecte tout le monde
                JsonObject endGame = Json.createObjectBuilder()
                    .add("type", "userHasDisconnected")
                    .build();
                for(Session player : players.values()) {
                    player.getBasicRemote().sendText(endGame.toString());
                }
            }
        }
        if(players.isEmpty()) {
            LobbyWS.resetUserList();
            Game.clearGameData();
            Game.hasStarted = false;
        }
        System.out.println("onClose: " + close_reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        System.err.println("Une erreur est survenue! ");
        JsonObject errorMsg = Json.createObjectBuilder()
            .add("type", "serverError")
            .build();
        String errorMsgStr = errorMsg.toString();
        for(Session player: players.values()) {
            try {
                player.getBasicRemote().sendText(errorMsgStr);
            } catch (IOException ex) {
                Logger.getLogger(CulDeChouetteWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LobbyWS.resetUserList();
        Game.clearGameData();
        Game.hasStarted = false;
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
        spectators.forEach((spectator) -> {
            try {
                spectator.getBasicRemote().sendText(nextPlayerStr);
            } catch (IOException ex) {
                Logger.getLogger(CulDeChouetteWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private void playerWinCv(String player) throws IOException, DAOException {
        int oldScore = scoreboard.get(player);
        int score = Game.calculateScore();
        scoreboard.put(player, oldScore + score);
        updateScoreboard();
    }
    
    private void playerLostSuite(String player) throws IOException, DAOException {
        int oldScore = scoreboard.get(player);
        scoreboard.put(player, oldScore - 10);
        updateScoreboard();
    }
    
    private void updatePlayerScore() throws IOException, DAOException {
        int score = Game.calculateScore();
        int oldScore = scoreboard.get(actualPlayer);
        scoreboard.put(actualPlayer, oldScore + score);
        updateScoreboard();
    }
    
    private void updateScoreboard() throws IOException, DAOException {
        int maxScore = 0;
        JsonArrayBuilder playersScoreArrayBuilder = Json.createArrayBuilder();
        for(Map.Entry<String, Integer> entry : scoreboard.entrySet()) {
            String player = entry.getKey();
            int score = entry.getValue();
            if(score > maxScore) {
                maxScore = score;
            }
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
        spectators.forEach((spectator) -> {
            try {
                spectator.getBasicRemote().sendText(scoreboardJson.toString());
            } catch (IOException ex) {
                Logger.getLogger(CulDeChouetteWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        if(maxScore >= Game.scoreMax) {
            System.out.println("Partie terminée !");
            if(maxScore > 0) {
                persistData();
            }
            JsonObject endGame = Json.createObjectBuilder()
                .add("type", "endGame")
                .build();
            for(Session player : players.values()) {
                player.getBasicRemote().sendText(endGame.toString());
            }
            maxScore = 0;
        }
    }
    
    private void persistData() throws DAOException {
        CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
        IPartie daoPartie = factory.getDAOPartie();
        IJoueur daoJoueur = factory.getDAOJoueur();
        IJoueursPartie daoJp = factory.getDAOJoueursPartie();
        Partie p = new Partie();
        p.setDatePartie(new Date());
        p.setScoreMax(Game.scoreMax);
        p.setNbCv(Game.nbCv);
        p.setNbSuites(Game.nbSuites);
        daoPartie.ajouterPartie(p);
        for(String player: playerOrder) {
            int score = scoreboard.get(player);
            int nbCvPerdues = 0;
            int nbSuitesGagnees = 0;
            for(int i = 0; i < Game.chouettesVeluesDataKey.size(); i++) {
                String currentPlayer = Game.chouettesVeluesDataKey.get(i);
                boolean cvLost = Game.chouettesVeluesDataObject.get(i);
                if(currentPlayer.equals(player) && !cvLost) {
                    nbCvPerdues++;
                }
            }
            for(int i = 0; i < Game.suitesDataKey.size(); i++) {
                String currentPlayer = Game.suitesDataKey.get(i);
                boolean suitesWin = Game.suitesDataObject.get(i);
                if(currentPlayer.equals(player) && suitesWin) {
                    nbSuitesGagnees++;
                }
            }
            Joueur j = daoJoueur.rechercherJoueurParPseudo(player);
            JoueursPartie jp = new JoueursPartie(j.getCodeJoueur(), p.getCodePartie());
            jp.setScore(score);
            jp.setCvPerdues(nbCvPerdues);
            jp.setSuiteGagnees(nbSuitesGagnees);
            jp.setPartie(p);
            jp.setJoueur(j);
            daoJp.ajouterJoueursPartie(jp);
            daoJoueur.updateAllJoueursStats();
        }
        System.out.println("Persist Party Data Ok");
        scoreboard.clear();
    }
    
    public static ArrayList<String> initUserOrder() {
        return LobbyWS.getUserList();
    }
}
