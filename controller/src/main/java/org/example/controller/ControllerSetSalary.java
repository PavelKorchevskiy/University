package org.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.constans.Parameters;
import org.example.excetions.IllegalDataException;
import org.example.excetions.IllegalFormatException;
import org.example.model.Teacher;
import org.example.repository.producer.TeacherProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/setSalary")
public class ControllerSetSalary extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerSetSalary.class);

  //админ выдает учителю одну зарплату
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    BigDecimal newSalary;
    try {
      newSalary = BigDecimal.valueOf(Double.parseDouble(req.getParameter(Parameters.SALARY)));
      log.info("new salary - " + newSalary);
    } catch (NumberFormatException e) {
      log.error("salary is not a number");
      throw new IllegalFormatException("salary is not a number");
    }
    int id;
    try {
      id = Integer.parseInt(req.getParameter(Parameters.ID_TEACHER));
      log.info("teachers id - " + id);
    } catch (NumberFormatException e) {
      log.error("id is not a number");
      throw new IllegalFormatException("id is not a number");
    }
    Optional<Teacher> optionalTeacher = TeacherProducer.getRepository()
        .findById(id);
    if (optionalTeacher.isPresent()) {
      Teacher teacher = optionalTeacher.get();
      teacher.getSalary().add(newSalary);
      TeacherProducer.getRepository().save(teacher);
    } else {
      throw new IllegalDataException("Teacher with id - " + id + " doesn't exist");
    }
    req.getRequestDispatcher("pages/AdminSetSalary.jsp").forward(req, resp);
  }
}
