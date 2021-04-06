package org.example.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.constans.Attributes;
import org.example.constans.Links;
import org.example.constans.Parameters;
import org.example.exceptions.IllegalDataException;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.producer.StudentProducer;
import org.example.service.Checking;
import org.example.service.StudentService;
import org.example.service.TeachersServ;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@WebServlet(Links.CHANGE_RATING)
@Controller
public class ControllerForChangeRating {

  //private final StudentServ studentServ;
  private final TeachersServ teachersServ = new TeachersServ();

  private static final Logger log = LoggerFactory.getLogger(ControllerForChangeRating.class);

  @PostMapping(path = Links.CHANGE_RATING)
  protected ModelAndView service(HttpServletRequest req)
      throws ServletException, IOException {
    ModelAndView modelAndView = new ModelAndView();
    HttpSession session = req.getSession();
    int rating = Checking.getRating(req.getParameter(Parameters.RATING));
    Subject subject = Checking.getSubject(req.getParameter(Parameters.SUBJECT));
    int id = Checking.getId(req.getParameter(Parameters.ID_STUDENT));
    log.info(String.format("subject - %s", subject.toString()));
    log.info(String.format("id - %s", id));
    String login = String.valueOf(session.getAttribute(Attributes.LOGIN));
    String password = String.valueOf(session.getAttribute(Attributes.PASSWORD));
    Teacher teacher = teachersServ.getTeacherWithLoginAngPassword(login, password);
    Optional<Student> studentOptional = teachersServ.getStudentById(teacher, id);
    if (studentOptional.isPresent() && teachersServ.getGroup(teacher).isPresent()
        && teachersServ.getGroup(teacher).get().getSubjects().contains(subject)) {
      Student student = studentOptional.get();
      //
      StudentService.putRating(student, subject, rating);
      StudentProducer.getRepository().save(student);
      //SaveService.saveStudent(StudentService.putRating(student, subject, rating));
    } else {
      throw new IllegalDataException(
          "Student with this subject doesn't exist or You can't put him rating");
    }
    session.setAttribute(Attributes.GROUP, teachersServ.showGroup(teacher));
    modelAndView.setViewName("TeacherPage");
    return modelAndView;
//    req.getRequestDispatcher("pages/TeacherPage.jsp").forward(req, resp);
  }
}
