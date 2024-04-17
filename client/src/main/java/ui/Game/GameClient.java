package ui.Game;

import chess.ChessMove;
import chess.ChessPosition;
import exceptions.DataAccessException;
import ui.Player;
import websocket.NotificationHandler;
import websocket.WebsocketFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class GameClient {
  private Scanner scanner;
  private WebsocketFacade facade;
  private GameBoard gameBoard;
  private String playerColor;
  private NotificationHandler notificationHandler;
  private Player playerInfo;

  public GameClient(GameBoard gameBoard, String serverURL, String playerColor, Player player) throws DataAccessException {
    this.gameBoard=gameBoard;
    this.scanner = new Scanner(System.in);
    this.facade = new WebsocketFacade(serverURL, notificationHandler);
    this.playerColor = playerColor;
    this.playerInfo = player;
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
    if (playerColor == null) {
      help ="""
            Help Menu
            Redraw : Redraws current game board
            Leave : Exit game
            Highlight : Highlight legal moves
            """;
    }
    return help;
  }

  private String redraw() {
    gameBoard.fancyPrint(playerColor, null, null);
    return "board redrawn";
  }
  private String highlightMoves() {
    System.out.println("Enter piece X position (1-8):");
    String pieceX = scanner.nextLine();
    System.out.println("Enter piece Y position (1-8):");
    String pieceY = scanner.nextLine();

    int col = Integer.parseInt(pieceX);
    int row = Integer.parseInt(pieceY);

    if (gameBoard.board.getPiece(new ChessPosition(row, col)) == null) {
      return "No piece at given position";
    }
    Collection<ChessMove> validMoves = gameBoard.game.validMoves(new ChessPosition(row, col));
    if (validMoves == null || validMoves.isEmpty()) {
      return "No valid moves for that piece";
    }

    ArrayList<ChessPosition> highlightSquares = new ArrayList<>();
    for (ChessMove move : validMoves) {
      highlightSquares.add(move.getEndPosition());
    }

    gameBoard.fancyPrint(playerColor, highlightSquares, new ChessPosition(row, col));
    return "Valid moves highlighted";
  }

  private String leave() {
    return "quit";
  }

  public String evalLine(String line) {
    try {
      var tokens = line.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
          case "quit" -> "quit";
          case "help" -> help();
          case "redraw" -> redraw();
          case "highlight" -> highlightMoves();
          case "leave" -> leave();
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
