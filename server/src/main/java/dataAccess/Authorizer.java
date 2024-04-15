package dataAccess;

//import dataAccess.DAO.memory.AuthDAO;
import dataAccess.DAO.SQL.AuthDAO;

import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import models.AuthToken;

public class Authorizer{
  private String username;

  public String authorize(String authToken) throws UnauthorizedException, DataAccessException {
    AuthDAO authDAO = new AuthDAO();

    AuthToken token = authDAO.readToken(authToken);

    if (token == null) throw new UnauthorizedException();

    this.username = token.getUsername();
    return this.username;
  }

}
