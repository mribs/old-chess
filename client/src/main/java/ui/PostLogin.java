package ui;

import chess.ChessGame;
import exceptions.DataAccessException;
import models.AuthToken;
import models.Game;
import models.Join;
import server.ServerFacade;
import websocket.WebsocketFacade;

public class PostLogin {
  private ServerFacade serverFacade;
  private WebsocketFacade websocketFacade;

  public PostLogin(ServerFacade serverFacade) {
    this.serverFacade=serverFacade;
    try {
      websocketFacade = new WebsocketFacade(serverFacade.serverUrl);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
    ChessGame gameJoined=null;
    try {
      gameJoined=serverFacade.joinGame(gameID, color, authToken.getUsername(), authToken.getAuthToken());
      websocketFacade.join(new webSocketMessages.userCommands.Join(authToken.getAuthToken(), gameID, color));
    } catch (Exception e) {
      System.out.println("Couldn't join game: " + e.getMessage());
    }
    return gameJoined;
  }

}