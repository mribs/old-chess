package server;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import models.Game;
import models.User;

import java.io.*;
import java.net.*;

public class ServerFacade {
  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl = url;
  }


  public User registerUser(User user) throws DataAccessException {
    var path = "/pet";
    return this.makeRequest("POST", path, user, User.class);
  }

  public void deletePet(int id) throws DataAccessException {
    var path = String.format("/pet/%s", id);
    this.makeRequest("DELETE", path, null, null);
  }

  public void deleteAllPets() throws DataAccessException {
    var path = "/pet";
    this.makeRequest("DELETE", path, null, null);
  }

  public Game[] listPets() throws DataAccessException {
    var path = "/pet";
    record listPetResponse(Game[] games) {
    }
    var response = this.makeRequest("GET", path, null, listPetResponse.class);
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
