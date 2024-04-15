package ui;

import models.AuthToken;
import models.User;
import server.ServerFacade;

public class PostLogin {
  private ServerFacade serverFacade;

  public PostLogin(ServerFacade serverFacade) {
    this.serverFacade=serverFacade;
  }
  public String helpMenu() {
    String help ="""
            Help Menu
            CreateGame : Create a new game
            ListGames : List the existing games
            JoinGame : Join an existing game as a player
            Observe : Join an existing game as an observer
            Logout : Logout
            """;
    return help;
  }

  public  String logout(AuthToken authToken) {
    try {
      serverFacade.logout(authToken.getAuthToken());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
