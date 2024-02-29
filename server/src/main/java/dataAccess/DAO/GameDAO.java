package dataAccess.DAO;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.models.Game;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameDAO {
  private static Map<Integer, Game>  gameMap = new ConcurrentHashMap<>();

  //inserts new game into database
  public Game insert(String gameName) throws DataAccessException {
    //create new game if gameName doesn't already exist
//    if (gameMap.containsKey(gameName)) throw new DataAccessException("bad request");

    Game game = new Game(gameName);
    gameMap.put(game.getGameID(), game);

    return game;
  }

  //finds game by gameID
  public Game find(Integer gameID){
    Game game = gameMap.get(gameID);
    return game;
  }

  //uses player's username to "claim" a spot in a game
  public void claimSpot(Integer gameID, Game game) throws DataAccessException {
    gameMap.replace(gameID, game);
  }

  //updates game moves in database
  public  void updateGame(String gameID) {}

  //removes game from database
  public void remove(Integer gameID) throws DataAccessException {
    gameMap.remove(gameID);
  }

  //clears database
  public void clearGames() {
    gameMap.clear();
  }

  public List<Game> getGames() {
    Collection<Game> gameList = gameMap.values();
    return new ArrayList<>(gameList);
  }
}
