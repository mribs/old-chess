package webSocketMessages.userCommands;

import chess.ChessGame;

public class Join extends UserGameCommand {
  private int gameID;
  private String playerColor;

  public Join(String authToken, int gameID, String playerColor) {
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
    this.commandType = CommandType.JOIN_PLAYER;
  }

  public String getPlayerColor() {
    return playerColor;
  }

  public int getGameID() {
    return gameID;
  }
}
