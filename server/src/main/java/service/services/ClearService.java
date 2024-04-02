package service.services;

import dataAccess.DAO.SQL.AuthDAO;
import dataAccess.DAO.memory.GameDAO;
import dataAccess.DAO.SQL.UserDAO;

public class ClearService {
  public void clear() {
    AuthDAO authDAO = new AuthDAO();
    GameDAO gameDAO = new GameDAO();
    UserDAO userDAO = new UserDAO();

    authDAO.clearTokens();
    gameDAO.clearGames();
    userDAO.clearUsers();
  }
}
