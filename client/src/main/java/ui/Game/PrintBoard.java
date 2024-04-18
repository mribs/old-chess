package ui.Game;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

import java.util.ArrayList;

public class PrintBoard {

  public void fancyPrint(ChessBoard board, String color, ArrayList<ChessPosition> highlightSquares, ChessPosition highlightPiece) {
    Boolean reverse = false;
    if (color != null) color = color.toLowerCase();
    if ("black".equals(color)) reverse = true;

    // Print column labels
    if (reverse) {
      for (char c = '8'; c >= '1'; c--) {
        System.out.print(c + "  " );
      }
    } else {
      for (char c = '1'; c <= '8'; c++) {
        System.out.print(c + "  ");
      }
    }
    System.out.println();

    for (int i = 1; i <= 8; i++) {
      int row = reverse ? i : 8 - i + 1;
      for (int j = 1; j <= 8; j++) {
        int col = reverse ? 8 - j + 1 : j;
        System.out.print(EscapeSequences.moveCursorToLocation(col * 5, (reverse ? 8 - row + 2 : row + 1)));

        boolean isWhiteSquare = (row + col) % 2 == 1;
        String backgroundColor = isWhiteSquare ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
        String textColor = EscapeSequences.SET_TEXT_COLOR_MAGENTA;

        if (highlightSquares != null && highlightSquares.contains(new ChessPosition(row, col))) {
          backgroundColor = EscapeSequences.SET_BG_COLOR_GREEN;
        }
        if (highlightPiece != null && highlightPiece.equals(new ChessPosition(row, col))) {
          backgroundColor = EscapeSequences.SET_BG_COLOR_YELLOW;
        }

        System.out.print(backgroundColor + textColor);

        ChessPiece piece = board.getPiece(new ChessPosition(row, col));
        if (piece == null)
          System.out.print(EscapeSequences.EMPTY);
        else if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE))
          System.out.print(printWhitePiece(piece));
        else
          System.out.print(printBlackPiece(piece));
        backgroundColor = EscapeSequences.RESET_BG_COLOR;
        textColor = EscapeSequences.SET_TEXT_COLOR_BLUE;
        System.out.print(backgroundColor + textColor);
      }
      System.out.println(row); // Move to the next line for the next row
    }
  }

  private String printWhitePiece(ChessPiece piece) {
    switch (piece.getPieceType()) {
      case PAWN: return EscapeSequences.WHITE_PAWN;
      case ROOK: return EscapeSequences.WHITE_ROOK;
      case KNIGHT: return EscapeSequences.WHITE_KNIGHT;
      case BISHOP: return EscapeSequences.WHITE_BISHOP;
      case QUEEN: return EscapeSequences.WHITE_QUEEN;
      case KING: return EscapeSequences.WHITE_KING;
    }
    return null;
  }

  private String printBlackPiece(ChessPiece piece) {
    switch (piece.getPieceType()) {
      case PAWN:
        return EscapeSequences.BLACK_PAWN;
      case ROOK:
        return EscapeSequences.BLACK_ROOK;
      case KNIGHT:
        return EscapeSequences.BLACK_KNIGHT;
      case BISHOP:
        return EscapeSequences.BLACK_BISHOP;
      case QUEEN:
        return EscapeSequences.BLACK_QUEEN;
      case KING:
        return EscapeSequences.BLACK_KING;
    }
    return null;
  }
}
