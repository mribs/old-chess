package ui;

import chess.ChessGame;
import exceptions.DataAccessException;
import models.AuthToken;
import models.Game;
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

  public void logout(AuthToken authToken) {
    try {
      serverFacade.logout(authToken.getAuthToken());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String createGame(AuthToken authToken, String gameName) {
    int gameID = 0;
    String returnString;
    try {
      gameID=serverFacade.createGame(authToken.getAuthToken(), gameName);
      returnString ="Created! The gameID is: " + gameID;
    } catch (DataAccessException e) {
      returnString = "Could not create the game: " + e.getMessage();
    }

    return returnString;
  }
  public Game[] listGames(AuthToken authToken) {
    Game[] games = null;
    try {
      games = serverFacade.listGames(authToken.getAuthToken());
    } catch (Exception e) {
      System.out.println( "Could not list games: " + e.getMessage());
    }
    return games;
  }

  public ChessGame joinGame(int gameID, String color, AuthToken authToken) {
    try {
      ChessGame joined = serverFacade.joinGame(gameID, color, authToken.getUsername(), authToken.getAuthToken());
      return joined;
    } catch (Exception e) {
      System.out.println("Couldn't join game: " + e.getMessage());
    }
    return null;
  }

}
