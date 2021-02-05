package org.example.controller;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.constans.Attributes;
import org.example.constans.Links;
import org.example.constans.Parameters;
import org.example.excetions.IllegalDataException;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.producer.StudentProducer;
import org.example.repository.producer.TeacherProducer;
import org.example.service.Checking;
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
    int rating = Checking.getRating(req.getParameter(Parameters.RATING));
    Subject subject = Checking.getSubject(req.getParameter(Parameters.SUBJECT));
    int id = Checking.getId(req.getParameter(Parameters.ID_STUDENT));
    log.info(String.format("subject - %s", subject.toString()));
    log.info(String.format("id - %s", id));
    RepositoryForTeachersInterface repository = TeacherProducer.getRepository();
    Optional<Teacher> teacherOptional = repository
        .findByLoginAndPassword(String.valueOf(session.getAttribute(Attributes.LOGIN)),
            String.valueOf(session.getAttribute(Attributes.PASSWORD)));
    if (teacherOptional.isPresent()) {
      Teacher teacher = teacherOptional.get();
      Optional<Student> studentOptional = TeacherService.getStudentById(teacher, id);
      if (studentOptional.isPresent() && TeacherService.getGroup(teacher).isPresent()
          && TeacherService.getGroup(teacher).get().getSubjects().contains(subject)) {
        Student student = studentOptional.get();
        student.putRating(subject, rating);
        StudentProducer.getRepository().save(student);
      } else {
        throw new IllegalDataException(
            "Student with this subject doesn't exist or You can't put him rating");
      }
    } else {
      throw new IllegalDataException("Teacher with this id doesn't exist");
    }
    req.getRequestDispatcher("pages/TeacherPage.jsp").forward(req, resp);
  }
}
