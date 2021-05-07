package org.example.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.example.aspects.TeacherAccess;
import org.example.constans.Attributes;
import org.example.constans.Links;
import org.example.constans.Parameters;
import org.example.exceptions.IllegalDataException;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.service.Checking;
import org.example.service.StudentService;
import org.example.service.ServiceCRUD;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerForChangeRating {

  private ServiceCRUD serviceCRUD;

  private static final Logger log = LoggerFactory.getLogger(ControllerForChangeRating.class);

  @PostMapping(path = Links.CHANGE_RATING)
  @TeacherAccess
  protected ModelAndView service(HttpServletRequest req)
      throws ServletException, IOException {
    ModelAndView modelAndView = new ModelAndView();
    HttpSession session = req.getSession();
    int rating = Checking.getRating(req.getParameter(Parameters.RATING));
    Subject subject = Checking.getSubject(req.getParameter(Parameters.SUBJECT));
    int id = Checking.getId(req.getParameter(Parameters.ID_STUDENT));
    Teacher teacher = serviceCRUD.getTeacherWithLoginAngPassword(String.valueOf(session.getAttribute(Attributes.LOGIN)), String.valueOf(session.getAttribute(Attributes.PASSWORD)));
    Optional<Student> studentOptional = serviceCRUD.getStudentById(teacher, id);
    if (studentOptional.isPresent() && serviceCRUD.getGroup(teacher).isPresent()
        && serviceCRUD.getGroup(teacher).get().getSubjects().contains(subject)) {
      Student student = studentOptional.get();
      StudentService.putRating(student, subject, rating);
      serviceCRUD.saveStudent(student);
    } else {
      throw new IllegalDataException(
          "Student with this subject doesn't exist or You can't put him rating");
    }
    session.setAttribute(Attributes.GROUP, serviceCRUD.showGroup(teacher));
    modelAndView.setViewName("TeacherPage");
    return modelAndView;
  }

  @Autowired
  public void setServiceCRUD(ServiceCRUD serviceCRUD) {
    this.serviceCRUD = serviceCRUD;
  }
}
