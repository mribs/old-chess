package serviceTests;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DAO.SQL.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {

  @Test
  void registerUser() {
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



  }
}