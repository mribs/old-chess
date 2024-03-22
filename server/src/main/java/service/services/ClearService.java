package service.services;

import dataAccess.DAO.memory.AuthDAO;
import dataAccess.DAO.memory.GameDAO;
import dataAccess.DAO.memory.UserDAO;

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
