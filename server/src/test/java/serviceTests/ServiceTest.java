package serviceTests;

import dataAccess.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

  @BeforeEach
  void setUp() {
    //set up database
    Database database = new Database();
    //add 1 user

    //add 1 game
  }

  @Test
  void clear() {
    //check for empty database
  }

  @Test
  void createGamePass() {
    //success
  }

  @Test
  void createGameFail() {

  }

  @Test
  void joinPass() {

  }
  @Test
  void joinFail() {

  }

  @Test
  void listPass() {

  }
  @Test
  void listFail() {

  }

  @ParameterizedTest
  @CsvSource()
  void login() {
    //good password
    //bad password

  }

  @Test
  void logoutPass() {

  }
  @Test
  void logoutFail() {

  }

  @Test
  void registerPass() {

  }
  @Test
  void registerFail() {

  }
}
