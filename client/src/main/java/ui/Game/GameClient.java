package ui.Game;

import exceptions.DataAccessException;
import websocket.NotificationHandler;
import websocket.WebsocketFacade;

import java.net.URL;
import java.util.Arrays;
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
    String help ="""
            Help Menu
            Redraw : Redraws current game board
            Leave : Exit game
            Highlight : Highlight legal moves
            Move : Make a move (if it's your turn)
            Resign : AKA rage quit
            """;
    return help;
  }

  public String evalLine(String line) {
    try {
      var tokens = line.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
          case "quit" -> "quit";
          case "help" -> help();
          default -> invalid();
        };
    } catch (Throwable e) {
      return e.getMessage();
    }
  }

  private String invalid() {
    return "Invalid option\n" + help();
  }
}
