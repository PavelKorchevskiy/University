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
import org.example.model.Teacher;
import org.example.repository.producer.TeacherProducer;
import org.example.service.Checking;

@WebServlet("/setSalary")
public class ControllerSetSalary extends HttpServlet {

  //админ выдает учителю одну зарплату
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    BigDecimal newSalary = Checking.getSalary(req.getParameter(Parameters.SALARY));
    int id = Checking.getId(req.getParameter(Parameters.ID_TEACHER));
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
