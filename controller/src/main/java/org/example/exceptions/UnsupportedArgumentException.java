package org.example.exceptions;

import javax.servlet.http.HttpServletRequest;

public class UnsupportedArgumentException extends ServletException {

  public UnsupportedArgumentException(String message, HttpServletRequest req) {
    super(message, req);
  }
}
