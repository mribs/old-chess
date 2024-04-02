package dataAccess.DAO.SQL;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.models.Game;
import dataAccess.models.User;

import java.util.ArrayList;
import java.util.Collection;

public class GameDAO extends DAO {

  //inserts new game into database
  public Game insert(String gameName) throws DataAccessException {
    Game game = new Game(gameName);
    int gameID = game.getGameID();
    var gameJSON = new Gson().toJson(game.getGame());
    String white = null;
    String black = null;
    String observers = null;


    var statement = "INSERT INTO game (gameID, gameName, game, whiteUsername, blackUsername, observers) VALUES (?, ?, ?, ?, ?, ?)";
    var json = new Gson().toJson(game);
    executeUpdate(statement, gameID, gameName, gameJSON, null, null, null, json);

    return game;
  }

  //finds game by gameID
  public Game find(Integer gameID){
    Game game = Database.gameMap.get(gameID);
    return game;
  }

  //uses player's username to "claim" a spot in a game
  public void claimSpot(Integer gameID, Game game) throws DataAccessException {
    Database.gameMap.replace(gameID, game);
  }

  //updates game moves in database
  public  void updateGame(String gameID) {}

  //removes game from database
  public void remove(Integer gameID) throws DataAccessException {
    var statement = "DELETE FROM game WHERE gameID=?";
    executeUpdate(statement, gameID);
  }

  //clears database
  public void clearGames() {
    var statement = "TRUNCATE game";
    try {
      executeUpdate(statement);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public ArrayList<Game> getGames() {
    Collection<Game> gameList = Database.gameMap.values();
    return new ArrayList<>(gameList);
  }
}
