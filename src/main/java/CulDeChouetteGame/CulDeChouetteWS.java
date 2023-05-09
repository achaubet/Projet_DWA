/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.io.IOException;
import javax.json.JsonObject;
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
    @OnOpen
    public void onOpen(Session session, EndpointConfig EndpointConfig) throws IOException {
        System.out.println ("WebSocket ouverte : "+session.getId());
        JsonObject openMessage = javax.json.Json.createObjectBuilder()
                .add("message", "Connecté avec succès à Java sur le serveur CulDeChouetteWS")
                .build();
        String messageString = openMessage.toString();
        session.getBasicRemote().sendText(messageString);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
