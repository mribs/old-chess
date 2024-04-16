package websocket;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketFacade extends Endpoint {
    Session session;
    NotificationHandler notificationHandler;


    public WebsocketFacade(String url, NotificationHandler notificationHandler) throws DataAccessException {
      try {
        url = url.replace("http", "ws");
        URI socketURI = new URI(url + "/connect");
        this.notificationHandler = notificationHandler;

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, socketURI);

        //set message handler
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
          @Override
          public void onMessage(String message) {
            ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
            notificationHandler.notify(notification);
          }
        });
      } catch (DeploymentException | IOException | URISyntaxException ex) {
        throw new DataAccessException(ex.getMessage());
      }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void enterPetShop(String visitorName){
//      try {
//        var action = new UserGameCommand(UserGameCommand.Type.ENTER, visitorName);
//        this.session.getBasicRemote().sendText(new Gson().toJson(action));
//      } catch (IOException ex) {
//        throw new ResponseException(500, ex.getMessage());
//      }
    }

    public void leavePetShop(String visitorName){
//      try {
//        var action = new Action(Action.Type.EXIT, visitorName);
//        this.session.getBasicRemote().sendText(new Gson().toJson(action));
//        this.session.close();
//      } catch (IOException ex) {
//        throw new ResponseException(500, ex.getMessage());
//      }
    }

}
