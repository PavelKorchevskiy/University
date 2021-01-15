package org.example.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.example.constans.Links;
import org.example.constans.Parameters;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.RepositoryForTeachersInMemory;
import org.example.repository.RepositoryForTeachersInterface;
import org.example.service.TeacherService;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(Links.CHANGE_RATING)
public class ControllerForChangeRating extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForChangeRating.class);

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    int rating = 0;
    try {
      rating = Integer.parseInt(req.getParameter(Parameters.RATING));
      log.info(String.valueOf(rating));
    } catch (NumberFormatException e) {
      log.error("not a number in rating");
    }
    String subjectString = req.getParameter(Parameters.SUBJECT);
    Subject subject = null;
    try {
      subject = Subject.valueOf(subjectString);
    } catch (IllegalArgumentException e) {
      log.info(String.format("subject is not correct - %s", subjectString));
    }
    int id = 0;
    try {
      id = Integer.parseInt(req.getParameter(Parameters.ID_STUDENT));
      log.info("students id - " + id);
    } catch (NumberFormatException e) {
      log.error("id is not a number");
    }
    log.info(String.format("subject - %s", subjectString));
    log.info(String.format("id - %s", id));
    RepositoryForTeachersInterface repository = RepositoryForTeachersInMemory.getInstance();
    Optional<Teacher> teacherOptional = repository
        .findByLoginAndPassword(String.valueOf(session.getAttribute("login")), String.valueOf(session.getAttribute("password")));
    if (teacherOptional.isPresent()) {
      Teacher teacher = teacherOptional.get();
      Optional<Student> studentOptional = TeacherService.getStudentById(teacher, id);
      if (studentOptional.isPresent() && TeacherService.getGroup(teacher).isPresent() &&TeacherService.getGroup(teacher).get().getSubjects().contains(subject)) {
        Student student = studentOptional.get();
        student.putRating(subject, rating);
      }
    }
    req.getRequestDispatcher("pages/TeacherPage.jsp").forward(req, resp);
  }
}
