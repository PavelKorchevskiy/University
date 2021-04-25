package org.example.exceptions;

public class IllegalFormatException extends AppException {

  public IllegalFormatException() {
  }

  public IllegalFormatException(String message) {
    super(message);
  }

  public IllegalFormatException(Throwable cause) {
    super(cause);
  }
}
