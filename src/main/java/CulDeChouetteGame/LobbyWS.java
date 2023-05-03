/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import static javax.json.Json.createReader;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
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
    
    // private static List<Session> sessions = new ArrayList<>();
    private static ConcurrentHashMap<String, Session> sessionsHM = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig EndpointConfig) throws IOException, Exception {
        System.out.println ("WebSocket ouverte : "+session.getId());
        // properties = config.getUserProperties();
        // sessions.add(session);
        //session.getBasicRemote().sendText("Connecté à Java avec succès!");  //sendObject("Connecté à Java avec succès !");
        JsonObject openMessage = javax.json.Json.createObjectBuilder()
                .add("message", "Connecté avec succès à Java")
                .build();
        String messageString = openMessage.toString();
        session.getBasicRemote().sendText(messageString);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("Message recu: " + message);
        JsonReader jsonReader = createReader(new StringReader(message)); // Transformation du message en JSON
        JsonObject jsonObject = jsonReader.readObject();
        if(jsonObject.containsKey("username")) {
            String username = jsonObject.getString("username");
            System.out.println(username);
            sessionsHM.put(username, session);
            updateUserList();
        }
        if(jsonObject.containsKey("message")) {
            switch(jsonObject.getString("message")) {
                case "requestUpdateUserList":
                    System.out.println("User list update send");
                    updateUserList();
                    break;
                case "inviteSelectedUsers":
                    System.out.println("Received user list");
                    JsonArray selectedPlayersJson = jsonObject.getJsonArray("users");
                    for(JsonValue player : selectedPlayersJson) {
                        System.out.println(((JsonString) player).getString());
                        if(sessionsHM.containsKey(((JsonString) player).getString())) {
                            System.out.println("Coucou2");
                            JsonObject invitationMessage = javax.json.Json.createObjectBuilder()
                                .add("type", "invitation")
                                .build();
                            String invitationString = invitationMessage.toString();
                            sessionsHM.get(((JsonString) player).getString()).getBasicRemote().sendText(invitationString);
                        }
                    }
                    break;
                case "redirectToGame":
                    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                    jsonObjectBuilder.add("type", "redirectToGame");
                    JsonObject jsonObjectredirect = jsonObjectBuilder.build();
                    String redirectMessage = jsonObjectredirect.toString();
                    for (Map.Entry<String, Session> entry : sessionsHM.entrySet()) {
                        entry.getValue().getBasicRemote().sendText(redirectMessage);
                    }
                break;
               
            }
        }
        jsonReader.close(); // Ne pas oublier de fermer le reader une fois le JSON parsé
    }
        
    @OnClose
    public void onClose(Session session, CloseReason close_reason) throws IOException, EncodeException {
        Iterator<Map.Entry<String, Session>> iterator = sessionsHM.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Session> entry = iterator.next();
            if(entry.getValue().equals(session)) {
                iterator.remove();
                updateUserList();
            }
        }
        System.out.println("onClose: " + close_reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError: " + throwable.getMessage());
    }
    
    
    private void updateUserList() throws IOException, EncodeException {
        List<String> users = new ArrayList<>(sessionsHM.keySet());
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("type", "userList");
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (String user: users) {
            jsonArrayBuilder.add(user);
        }
        jsonObjectBuilder.add("users", jsonArrayBuilder.build());
        JsonObject jsonObject = jsonObjectBuilder.build();
        String userListStr = jsonObject.toString();
        for (Map.Entry<String, Session> entry : sessionsHM.entrySet()) {
            entry.getValue().getBasicRemote().sendText(userListStr);
        }
    }
}
