package org.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.constans.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForChangeRating.class);

 @PostMapping("/logout")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final HttpSession session = req.getSession();
    session.removeAttribute(Attributes.PASSWORD);
    session.removeAttribute(Attributes.LOGIN);
    session.removeAttribute(Attributes.ROLE);
    log.info("logout");
    try {
      req.getRequestDispatcher("pages/LoginPage.jsp").forward(req, resp);
    } catch (ServletException e) {
      e.printStackTrace();
    }
 }
}
