package org.example.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.model.Student;
import org.example.repository.RepositoryForStudentsInMemory;
import org.example.repository.RepositoryForStudentsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/rating")
public class ControllerForShowRating extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForShowRating.class);

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String login = req.getParameter("login");
    RepositoryForStudentsInterface repository = RepositoryForStudentsInMemory.getInstance();
    Optional<Student> student = repository.findByLogin(login);
    String answer;
    if (student.isPresent()) {
      answer = student.get().getRatingAsString();
    } else {
      answer = "Пользаватель с таким логином не найден";
    }
    log.info("answer" + answer);
    HttpSession session = req.getSession();
    session.setAttribute("answer", answer);
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/showRating");
    requestDispatcher.forward(req, resp);
  }
}
