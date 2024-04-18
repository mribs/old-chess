package webSocketMessages.userCommands;

import chess.ChessMove;

public class Move extends UserGameCommand {
  public final int gameID;
  public final ChessMove move;

  public Move(String authToken, int gameID, ChessMove move) {
    super(authToken);
    this.gameID = gameID;
    this.move = move;
    this.commandType = CommandType.MAKE_MOVE;
  }

  public ChessMove getMove() {
    return move;
  }

  public int getGameID() {
    return gameID;
  }
}
