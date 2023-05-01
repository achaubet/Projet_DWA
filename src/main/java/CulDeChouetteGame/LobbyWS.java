/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.JsonObject;
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
@ServerEndpoint("/LobbyWS")
public class LobbyWS {
    
    private static List<Session> sessions = new ArrayList<>();
    private static ConcurrentHashMap<String, Session> sessionsHM = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig EndpointConfig) throws IOException, Exception {
        System.out.println ("WebSocket ouverte : "+session.getId());
        // properties = config.getUserProperties();
        sessions.add(session);
        session.getBasicRemote().sendText("Connecté à Java avec succès!");  //sendObject("Connecté à Java avec succès !");
    }
    
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message recu: " + message);
        JsonReader jsonReader = javax.json.Json.createReader(new java.io.StringReader(message)); // Transformation du message en JSON
        JsonObject jsonObject = jsonReader.readObject();
        if(jsonObject.containsKey("username")) {
            System.out.println(jsonObject.get("username"));
        }
        jsonReader.close(); // Ne pas oublier de fermer le reader une fois le JSON parsé
    }
        
    @OnClose
    public void onClose(Session session, CloseReason close_reason) {
        sessions.remove(session);
        System.out.println("onClose: " + close_reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError: " + throwable.getMessage());
    }
    
    
    private void updateUserList() {
    
    }
}
