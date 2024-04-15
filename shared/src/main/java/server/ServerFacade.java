package server;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import models.AuthToken;
import models.Game;
import models.User;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;

public class ServerFacade {
  private final String serverUrl;
  private final HttpClient httpClient;
  private Gson gson;

  public ServerFacade(String url) {
    this.serverUrl = url;
    this.httpClient = HttpClient.newHttpClient();
    this.gson = new Gson();
  }


  public AuthToken registerUser(User user) throws DataAccessException {
    var path = "/user";
    return this.makeRequest("POST", path, user, AuthToken.class);
  }

  public AuthToken login(User user) throws DataAccessException {
    var path = "/session";
    return this.makeRequest("POST", path,user, AuthToken.class);
  }

  public void logout(String authToken) throws DataAccessException {
    var path = "/session";
    this.makeRequest("DELETE", path, null, authToken, null);
  }

  public int createGame(String authToken, String gameName) throws DataAccessException {
    Game game = new Game(gameName);
    var path = "/game";
    int gameID = 0;
    Game returnedGame = this.makeRequest("POST", path, game, authToken, Game.class);
    gameID = returnedGame.getGameID();
    return gameID;
  }

  public void deletePet(int id) throws DataAccessException {
    var path = String.format("/pet/%s", id);
    this.makeRequest("DELETE", path, null, null);
  }

  public void deleteAllPets() throws DataAccessException {
    var path = "/pet";
    this.makeRequest("DELETE", path, null, null);
  }

  public Game[] listGames(String authToken) throws DataAccessException {
    var path = "/game";
    record listGamesResponse(Game[] games) {
    }
    var response = this.makeRequest("GET", path, null, authToken, listGamesResponse.class);
    return response.games();
  }

  private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws DataAccessException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new DataAccessException(ex.getMessage());
    }
  }
  private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) throws DataAccessException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setRequestProperty("authorization", authToken);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new DataAccessException(ex.getMessage());
    }
  }


  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new DataAccessException("failure: " + status);
    }
  }

  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }


  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }



}
