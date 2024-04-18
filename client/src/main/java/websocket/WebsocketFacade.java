package websocket;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exceptions.DataAccessException;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketFacade extends Endpoint {
    Session session;
    NotificationHandler notificationHandler;
    public ChessBoard board = new ChessBoard();
    public ChessGame game;


    public WebsocketFacade(String url) throws DataAccessException {
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
            ServerMessage action=new Gson().fromJson(message, ServerMessage.class);
            switch (action.getServerMessageType()) {
              case NOTIFICATION -> notification(message);
              case LOAD_GAME -> loadGame(message);
              case ERROR -> error(message);
            }
          }
        });
      } catch (DeploymentException e) {
        throw new RuntimeException(e);
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  private void notification(String message) {
    Notification notification = new Gson().fromJson(message, Notification.class);
    System.out.println(notification.getMessage());
    System.out.println("[IN_GAME] >>>");
  }
  private void loadGame(String message) {
    System.out.println("Game loaded");
    LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);


    board = loadGame.game.getBoard();
    game = loadGame.game;
    System.out.print("[SIGNED_IN] >>>");
  }

  private void error(String message) {
    Error error = new Gson().fromJson(message, Error.class);
    System.out.println("Error received");
    System.out.println(error.getErrorMessage());
    System.out.print("[IN_GAME] >>>");
  }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

  public void join(Join join) throws Exception {
    try {
      this.session.getBasicRemote().sendText(new Gson().toJson(join));

    } catch (IOException ex) {
      System.out.println("Websocket error");
    }
  }

  public void observe(Observe join) throws Exception {
    try {
      this.session.getBasicRemote().sendText(new Gson().toJson(join));
    } catch (IOException ex) {
      System.out.println("Websocket error");
    }
  }

  public void leaveGame(Leave leave) throws Exception {
    try {
      this.session.getBasicRemote().sendText(new Gson().toJson(leave));
    } catch (IOException ex) {
      System.out.println("Websocket error");
    }
  }

  public void resign(Resign resign) throws Exception {
    try {
      this.session.getBasicRemote().sendText(new Gson().toJson(resign));
    } catch (IOException ex) {
      System.out.println("Websocket error");
    }
  }

  public void makeMove(Move move) throws Exception {
    try {
      this.session.getBasicRemote().sendText(new Gson().toJson(move));
    } catch (IOException ex) {
      System.out.println("Websocket error");
    }
  }
}
