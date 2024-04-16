package ui.Game;

import exceptions.DataAccessException;
import websocket.NotificationHandler;
import websocket.WebsocketFacade;

import java.net.URL;
import java.util.Scanner;

public class GameClient {
  private Scanner scanner;
  private WebsocketFacade facade;
  private GameBoard gameBoard;
  private NotificationHandler notificationHandler;

  public GameClient(GameBoard gameBoard, String serverURL) throws DataAccessException {
    this.gameBoard=gameBoard;
    this.scanner = new Scanner(System.in);
    this.facade = new WebsocketFacade(serverURL, notificationHandler);
  }

  public String help() {
  }
}
