package dataAccessTests;

import dataAccess.DAO.SQL.AuthDAO;
import dataAccess.DAO.SQL.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.models.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
  @BeforeEach
  void clearAuthtokenTable() {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clearTokens();
  }

  @Test
  void createTokenTest() {
    AuthDAO authDAO = new AuthDAO();
    String userName = "TestUserName";

    assertDoesNotThrow(() -> authDAO.createToken(userName));
  }

  @Test
  void readTokenTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    AuthToken authToken = authDAO.createToken("testUserName");

    assertNotNull(authDAO.readToken(authToken.getAuthToken()));
  }
  @Test
  void failReadTokenTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    assertNull(authDAO.readToken("doesn'tExist"));
  }

}