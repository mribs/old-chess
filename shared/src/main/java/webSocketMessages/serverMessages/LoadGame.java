package webSocketMessages.serverMessages;

public class LoadGame {
  public chess.ChessGame game;

  public LoadGame(chess.ChessGame game) {
    super();
    this.game = game;
  }

  public chess.ChessGame getGameData() {
    return game;
  }
}
