package webSocketMessages.userCommands;

public class Observe extends UserGameCommand {
  public int gameID;

  public Observe(String authToken, int gameID) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = CommandType.JOIN_OBSERVER;
  }

  public int getGameID() {
    return gameID;
  }
}
