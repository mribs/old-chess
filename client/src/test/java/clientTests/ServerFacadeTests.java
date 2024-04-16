package clientTests;

import dataAccess.DAO.SQL.AuthDAO;
import dataAccess.DAO.SQL.GameDAO;
import dataAccess.DAO.SQL.UserDAO;
import exceptions.DataAccessException;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }
    @AfterEach
    public void clear() {
        new AuthDAO().clearTokens();
        new UserDAO().clearUsers();
        new GameDAO().clearGames();
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void regPassTest() {
        assertDoesNotThrow(() ->
            facade.registerUser(new User("testname", "testPass", "testemail"))
        );
    }
    @Test
    public void regFailTest() {
        assertThrows(DataAccessException.class, () -> {
            facade.registerUser(new User("name", "pass"));
        });
    }

    @Test
    public void loginPassTest() {
        assertDoesNotThrow(() ->
                facade.registerUser(new User("testname", "testPass", "testemail"))
        );
        assertDoesNotThrow(() ->
                facade.login(new User("testname", "testPass"))
        );
    }
    @Test
    public void loginFailTest() {
        assertThrows(DataAccessException.class, () -> {
            facade.login(new User("name", "pass"));
        });
    }

    @Test
    public void logoutPassTEst() {
        AuthToken token=null;
        try {
            token=facade.registerUser(new User("testname", "testPass", "testemail"));
        } catch (DataAccessException e) {
            System.out.println("register failed: " + e.getMessage());
        }
        AuthToken finalToken=token;
        assertDoesNotThrow(() ->
                facade.logout(finalToken.getAuthToken())
        );
    }
    @Test
    public void logoutFailTest() {
        assertThrows(DataAccessException.class, () -> {
            facade.logout("anytoken");
        });
    }




}
