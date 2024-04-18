package websocket;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    System.out.println("Message: " + message);
    connections.broadcast(null, message);
  }

  private void enter(String visitorName, Session session) throws IOException {
//    connections.add(visitorName, session);
//    var message = String.format("%s is in the shop", visitorName);
//    var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
//    connections.broadcast(visitorName, notification);
  }

  private void exit(String visitorName) throws IOException {
    System.out.println("made it this far");
//    connections.remove(visitorName);
//    var message = String.format("%s left the shop", visitorName);
//    var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
//    connections.broadcast(visitorName, notification);
  }

//  public void makeNoise(String petName, String sound) throws ResponseException {
//    try {
//      var message = String.format("%s says %s", petName, sound);
//      var notification = new Notification(Notification.Type.NOISE, message);
//      connections.broadcast("", notification);
//    } catch (Exception ex) {
//      throw new ResponseException(500, ex.getMessage());
//    }
//  }
}
