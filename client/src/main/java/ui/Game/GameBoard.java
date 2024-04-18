package ui.Game;

import chess.*;
import exceptions.DataAccessException;
import ui.EscapeSequences;
import ui.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
  public ChessGame game;
  public ChessBoard board;
  private String serverUrl;
  private Player player;

  public GameBoard(Player player) {
    this.game = new ChessGame();
    this.board = game.getBoard();
    this.player = player;
    this.serverUrl = player.getServerUrl();

  }

  public void startGame(ChessGame game, String playerColor) throws DataAccessException {
    this.game = game;
    this.board = game.getBoard();
    board.resetBoard();

    //print board
    new PrintBoard().fancyPrint(board, playerColor, null, null);

    GamePlayUI gamePlayUI = new GamePlayUI(this, serverUrl, playerColor, player);

    //send joined message
    if (playerColor == null) {
      //send observer joined message
    }
    else {
      //send join message with color
    }
    gamePlayUI.run();
  }

  public String makeMove(ChessPosition start, ChessPosition end){
    ChessPiece.PieceType promotion = null;
    if (game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK) || game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
      return "Game is over";
    }
    if (board.getPiece(start).equals(ChessPiece.PieceType.PAWN)) {
      //TODO: something about getting a promotion piece
    }
    try {
      game.makeMove(new ChessMove(start, end, promotion));
    } catch (Throwable e) {
      return "Invalid move";
    }
    return "Move made";
  }
}
