package org.example.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForChangeRating.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final HttpSession session = req.getSession();
    session.removeAttribute("password");
    session.removeAttribute("login");
    session.removeAttribute("role");
    log.info("logout");
    resp.sendRedirect(super.getServletContext().getContextPath());
  }
}
