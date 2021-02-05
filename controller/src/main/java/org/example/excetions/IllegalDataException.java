package org.example.excetions;

public class IllegalDataException extends AppException {

  public IllegalDataException() {
  }

  public IllegalDataException(String message) {
    super(message);
  }

  public IllegalDataException(Throwable cause) {
    super(cause);
  }
}
