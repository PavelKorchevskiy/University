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
import org.example.constans.Attributes;
import org.example.exceptions.IllegalFormatException;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.producer.StudentProducer;
import org.example.service.Checking;
import org.example.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//пока не используется
@WebServlet("/rating")
public class ControllerForShowRating extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForShowRating.class);

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int id = Checking.getId(req.getParameter("idStudent"));
    RepositoryForStudentsInterface repository = StudentProducer.getRepository();
    Optional<Student> student = repository.findById(id);
    String rating = student.map(StudentService::getRatingAsString)
        .orElse("Пользаватель с таким логином не найден");
    log.info("rating" + rating);
    HttpSession session = req.getSession();
    session.setAttribute(Attributes.RATING,
        rating + " if you see this massage in web-browser please write me 375333038034");
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/showRating");
    requestDispatcher.forward(req, resp);
  }
}
