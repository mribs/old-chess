package ui.Game;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import exceptions.DataAccessException;
import ui.Player;
import websocket.NotificationHandler;
import websocket.WebsocketFacade;

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
    this.facade = new WebsocketFacade(serverURL);
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
    new PrintBoard().fancyPrint(gameBoard.board, playerColor, null, null);
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

    new PrintBoard().fancyPrint(gameBoard.board, playerColor, highlightSquares, new ChessPosition(row, col));
    return "Valid moves highlighted";
  }
  private String makeMove() {
    System.out.println("Enter piece X position (1-8):");
    String pieceX = scanner.nextLine();
    System.out.println("Enter piece Y position (1-8):");
    String pieceY = scanner.nextLine();

    int col = Integer.parseInt(pieceX);
    int row = Integer.parseInt(pieceY);
    ChessPosition start = new ChessPosition(row, col);

    System.out.println("Enter goal X position (1-8):");
    String pieceXF = scanner.nextLine();
    System.out.println("Enter goal Y position (1-8):");
    String pieceYF = scanner.nextLine();

    int colF = Integer.parseInt(pieceXF);
    int rowF = Integer.parseInt(pieceYF);
    ChessPosition end = new ChessPosition(rowF, colF);

    String returnString = gameBoard.makeMove(start, end);
    redraw();
    return returnString;

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
          case "move" -> makeMove();
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
