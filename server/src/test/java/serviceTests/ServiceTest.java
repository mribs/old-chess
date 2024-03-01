package serviceTests;

import dataAccess.*;
import dataAccess.models.AuthToken;
import dataAccess.models.Game;
import dataAccess.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import service.requests.CreateGameRequest;
import service.requests.JoinGameRequest;
import service.requests.ListGamesRequest;
import service.results.CreateGameResult;
import service.results.JoinGameResult;
import service.results.ListGamesResult;
import service.services.ClearService;
import service.services.CreateGameService;
import service.services.JoinGameService;
import service.services.ListGamesService;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

  @BeforeEach
  void setUp() {

    //add 1 user
    TempDatabase.userMap.put("testUser", new User("testUser", "testPass", "test"));
    TempDatabase.authTokenMap.put("testAuth", new AuthToken("testUser", "testAuth"));
    //add 1 game
    TempDatabase.gameMap.put(1, new Game("testGame"));
  }

  @AfterEach
  void takeDown() {
    TempDatabase.gameMap.clear();
    TempDatabase.authTokenMap.clear();
    TempDatabase.userMap.clear();
  }

  @Test
  void clear() {
    new ClearService().clear();
    //check for empty database
    assertTrue(TempDatabase.authTokenMap.isEmpty());
    assertTrue(TempDatabase.userMap.isEmpty());
    assertTrue(TempDatabase.gameMap.isEmpty());
  }

  @Test
  void createGamePass() throws DataAccessException, UnauthorizedException, BadRequestException {
    CreateGameResult result = new CreateGameService().createGame(new CreateGameRequest("new Game"));
    System.out.println(result.toString());

    assertTrue(TempDatabase.gameMap.containsKey(result.getGameID()));
  }

  @Test
  void createGameFail() {
    assertThrows(BadRequestException.class, () -> {
      //test no game name
      CreateGameResult result = new CreateGameService().createGame(new CreateGameRequest(""));
    });

  }

  @Test
  void joinPass() throws BadRequestException, DataAccessException, AlreadyTakenException {
    JoinGameResult result = new JoinGameService().join(new JoinGameRequest(1, "white"), "testUser");
    Game game = TempDatabase.gameMap.get(1);
    assertTrue(game.getWhiteUsername() == "testUser");

  }
  @Test
  void joinFail() throws BadRequestException, DataAccessException, AlreadyTakenException {
    TempDatabase.userMap.put("testUser2", new User("meanUser", "password", "email"));
    new JoinGameService().join(new JoinGameRequest(1, "White"), "meanUser");
    assertThrows(AlreadyTakenException.class, () -> {
      new JoinGameService().join(new JoinGameRequest(1, "White"), "testUser");
    });

  }

  @Test
  void listPass() {
    ListGamesResult result= new ListGamesService().listGames(new ListGamesRequest("testAuth"));

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
