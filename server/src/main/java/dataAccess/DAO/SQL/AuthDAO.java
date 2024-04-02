package dataAccess.DAO.SQL;

import com.google.gson.Gson;
import dataAccess.*;
import dataAccess.models.AuthToken;
import dataAccess.models.User;

import java.util.UUID;

public class AuthDAO extends DAO{
  //creates a new authToken object
  public AuthToken createToken(String username) throws DataAccessException {
    String authTokenString = UUID.randomUUID().toString();
    AuthToken newAuthToken = new AuthToken(username, authTokenString);

    if (newAuthToken == null) throw new DataAccessException("auth token didn't go");

    var statement = "INSERT INTO authtoken (username, authtoken) VALUES (?, ?)";
//    var json = new Gson().toJson(newAuthToken);
    executeUpdate(statement, newAuthToken.getUsername(), newAuthToken.getAuthToken());

    return newAuthToken;
  }

  //reads an authToken object
  public AuthToken readToken(String authToken) {
    AuthToken authToken1 = TempDatabase.authTokenMap.get(authToken);
    return authToken1;
  }
  //update an authToken object
  public void updateToken(String currAuthToken, AuthToken newAuthToken) throws UnauthorizedException {
    if (TempDatabase.authTokenMap.containsKey(currAuthToken)) {
      TempDatabase.authTokenMap.put(currAuthToken, newAuthToken);
    }
    else throw new UnauthorizedException();
  }
  //delete an authToken object
  public AuthToken deleteToken(String authToken) throws UnauthorizedException {
    AuthToken oldAuthToken = TempDatabase.authTokenMap.remove(authToken);
    if (oldAuthToken == null) {
      throw new UnauthorizedException();
    }
    return oldAuthToken;
  }

  public void clearTokens() {
    TempDatabase.authTokenMap.clear();
  }
}
