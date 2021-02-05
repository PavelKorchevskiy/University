package org.example.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletException extends RuntimeException {

  public ServletException() {
    super();
  }

  public ServletException(String message, HttpServletRequest req) {
    super(message);
    HttpSession session = req.getSession();
    session.setAttribute("exception", message);
  }
}
