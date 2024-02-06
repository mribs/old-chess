package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor whosTurn;
    ChessBoard gameBoard;

    public ChessGame() {
        whosTurn = TeamColor.WHITE;
        gameBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return whosTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        whosTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        Collection<ChessMove> trueValidMoves = new HashSet<>();
        //get piece at location
        ChessPiece piece =getBoard().getPiece(startPosition);

        //get that piece's moves
        validMoves = piece.pieceMoves(gameBoard, startPosition);
        if (validMoves == null) return null;
        //check if any are legal (don't leave king in danger)
        for (ChessMove move : validMoves) {
            //check for check
            ChessGame testGame = new ChessGame();
            ChessBoard testBoard = new ChessBoard();
            for (int r = 1; r <= 8; r++ ) {
                for (int c = 1; c <= 8; c++) {
                    if (gameBoard.getPiece(new ChessPosition(r,c)) != null) {
                        testBoard.addPiece(new ChessPosition(r, c), gameBoard.getPiece(new ChessPosition(r, c)));
                    }
                }
            }
            testGame.setBoard(testBoard);
            testBoard.removePiece(move.getStartPosition());
            testBoard.addPiece(move.getEndPosition(), piece);
            if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    testBoard.setWhiteKingPos(move.getEndPosition());
                }
                if (piece.getTeamColor() == TeamColor.BLACK) {
                    testBoard.setBlackKingPos(move.getEndPosition());
                }
            }
            if (testGame.isInCheck(piece.getTeamColor()) == false) {
                trueValidMoves.add(move);
            }

        }

        if (trueValidMoves == null) {
            return null;
        }
        return trueValidMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = null;

        switch (teamColor) {
            case BLACK:
                kingPos = gameBoard.getBlackKingPos();
                break;
            case WHITE:
                kingPos = gameBoard.getWhiteKingPos();
                break;
        }
//    System.out.println(teamColor + " king");
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameBoard.getPiece(new ChessPosition(r, c));
                if (piece == null) continue;
                if (piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(gameBoard, new ChessPosition(r, c));
                    if (moves != null) {
                        for (ChessMove move : moves) {
                            if (move.getEndPosition().equals(kingPos)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }
}
