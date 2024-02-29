package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import dataAccess.models.AuthToken;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthDAO {
  static Map<String, AuthToken> authTokenMap = new ConcurrentHashMap<>();
//creates a new authToken object
  public AuthToken createToken(String username) throws DataAccessException {
    String authTokenString = UUID.randomUUID().toString();
    AuthToken newAuthToken = new AuthToken(username, authTokenString);

    authTokenMap.put(authTokenString, newAuthToken);
    return newAuthToken;
  }

  //reads an authToken object
  public AuthToken readToken(String authToken) {
    AuthToken authToken1 = authTokenMap.get(authToken);
    return authToken1;
  }
//update an authToken object
  public void updateToken(String currAuthToken, AuthToken newAuthToken) throws UnauthorizedException {
    if (authTokenMap.containsKey(currAuthToken)) {
      authTokenMap.put(currAuthToken, newAuthToken);
    }
    else throw new UnauthorizedException();
  }
//delete an authToken object
  public AuthToken deleteToken(String authToken) throws UnauthorizedException {
    AuthToken oldAuthToken = authTokenMap.remove(authToken);
    if (oldAuthToken == null) {
      throw new UnauthorizedException();
    }
    return oldAuthToken;
  }

  public void clearTokens() {
    authTokenMap.clear();
  }
}
