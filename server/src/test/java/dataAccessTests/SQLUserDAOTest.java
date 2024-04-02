package dataAccessTests;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DAO.SQL.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {
  @BeforeEach
  void clearUserTable() {
    UserDAO userDAO = new UserDAO();
    userDAO.clearUsers();
  }

  @Test
  void createUserTest() {
        UserDAO userDAO = new UserDAO();
        try {
          userDAO.createUser(new User("testName", "testPass", "testEmail"));
        } catch (BadRequestException e) {
          throw new RuntimeException(e);
        } catch (AlreadyTakenException e) {
          throw new RuntimeException(e);
        } catch (DataAccessException e) {
          throw new RuntimeException(e);
        }

        try {
          User testUser = userDAO.readUser("testName");
          assertNotEquals(null, testUser);
        } catch (DataAccessException e) {
          throw new RuntimeException(e);
        }
  }

}