package dataAccessTests;

import dataAccess.DAO.SQL.AuthDAO;
import dataAccess.DAO.SQL.GameDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {

  @BeforeEach
  void clearGameTable() {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clearGames();
  }

  @Test
  void createGameTest() {
    GameDAO gameDAO = new GameDAO();
    String gameName = "Test Game";

    assertDoesNotThrow(() -> gameDAO.insert(gameName));
  }

}