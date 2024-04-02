package dataAccess.DAO.SQL;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.DatabaseManager;
import dataAccess.models.AuthToken;
import dataAccess.models.Game;
import dataAccess.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameDAO extends DAO {

  //inserts new game into database
  public Game insert(String gameName) throws DataAccessException {
    Game game = new Game(gameName);
    int gameID = game.getGameID();
    var gameJSON = new Gson().toJson(game.getGame());


    var statement = "INSERT INTO game (gameID, gameName, game, whiteUsername, blackUsername, observers) VALUES (?, ?, ?, ?, ?, ?)";
    executeUpdate(statement, String.valueOf(gameID), gameName, gameJSON,null,null,null);

    return game;
  }

  //finds game by gameID
  public Game find(Integer gameID) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      var statement = "SELECT gameID, gameName, game, whiteUsername, blackUsername, observers FROM game WHERE gameID=?";
      try (var ps = conn.prepareStatement(statement)) {
        ps.setString(1, String.valueOf(gameID));
        try (var rs = ps.executeQuery()) {
          if (rs.next()) {
            return readGameInfo(rs);
          }
        }
      }
    } catch (Exception e) {
      throw new DataAccessException("something went wrong");
    }
    return null;
  }

  private Game readGameInfo(ResultSet rs) throws SQLException {
    var gameID = rs.getInt("gameID");
    var gameName = rs.getString("gameName");
    var game = rs.getString("game");
    var whiteUsername = rs.getString("whiteUsername");
    var blackUsername = rs.getString("blackUsername");
    var observers = rs.getString("observers");

    ArrayList watchers = new ArrayList<>();
    ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
    return new Game(gameID, whiteUsername, blackUsername, watchers, gameName, chessGame);
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
