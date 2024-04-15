package ui;

import java.util.Arrays;

public class Player {
  private Boolean loggedIn;
  private PreLogin preLogin;
  private PostLogin postLogin;

  public Player() {
    this.loggedIn = false;
    this.preLogin = new PreLogin();
    this.postLogin = new PostLogin();
  }

  public String help() {
    if (loggedIn) {
      return postLogin.helpMenu();
    }
    return preLogin.helpMenu();
  }

  public Boolean getLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(Boolean loggedIn) {
    this.loggedIn=loggedIn;
  }

  public String evalLine(String line) {
    try {
      var tokens = line.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      if (!loggedIn) {
        return switch (cmd) {
          case "quit" -> "quit";
          default -> help();
        };
      }
      else {
        return switch (cmd) {
          default -> help();
        };
      }
    } catch (Throwable e) {
      return e.getMessage();
    }
  }
}
