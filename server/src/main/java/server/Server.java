package server;

import exceptions.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort){
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
//        /create Routes
        Handler clearHandler = new ClearHandler();
        Handler createHandler = new CreateGameHandler();
        Handler joinHandler = new JoinGameHandler();
        Handler listHandler = new ListGamesHandler();
        Handler loginHandler = new LoginHandler();
        Handler logoutHandler = new LogoutHandler();
        Handler registerHandler = new RegisterHandler();

        clearHandler.setupRoutes();
        createHandler.setupRoutes();
        joinHandler.setupRoutes();
        listHandler.setupRoutes();
        loginHandler.setupRoutes();
        logoutHandler.setupRoutes();
        registerHandler.setupRoutes();

        Spark.awaitInitialization();

        //connect to the database
        try{
            DatabaseManager.configureDatabase();
        }
        catch (DataAccessException ex) {
            stop();
        }
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
