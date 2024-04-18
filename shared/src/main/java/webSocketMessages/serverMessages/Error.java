package webSocketMessages.serverMessages;

public class Error {
  public String errorMessage;

  public Error(String message) {
    this.errorMessage = message;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}

