package org.example.excetions;

public class AppException extends RuntimeException {

  public AppException() {
  }

  public AppException(String message) {
    super(message);
  }

  public AppException(Throwable cause) {
    super(cause);
  }
}
