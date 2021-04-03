package org.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.constans.Parameters;
import org.example.model.Teacher;
import org.example.service.Checking;
import org.example.service.TeachersServ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

//@WebServlet("/setSalary")
@Controller
@AllArgsConstructor
public class ControllerSetSalary {

  private final TeachersServ service;

  @PostMapping("/setSalary")
  protected ModelAndView service(HttpServletRequest req)
      throws ServletException, IOException {
    ModelAndView modelAndView = new ModelAndView();
    BigDecimal newSalary = Checking.getSalary(req.getParameter(Parameters.SALARY));
    int id = Checking.getId(req.getParameter(Parameters.ID_TEACHER));
    Teacher teacher = service.getTeacherWithId(id);
    teacher.getSalary().add(newSalary);
    service.saveTeacher(teacher);
    modelAndView.setViewName("AdminSetSalary");
    return modelAndView;
    //req.getRequestDispatcher("pages/AdminSetSalary.jsp").forward(req, resp);
  }
}
