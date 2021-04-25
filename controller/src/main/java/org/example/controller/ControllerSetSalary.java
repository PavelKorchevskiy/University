package org.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.example.constans.Attributes;
import org.example.constans.Parameters;
import org.example.model.Teacher;
import org.example.service.Checking;
import org.example.service.ServiceCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerSetSalary {

  private ServiceCRUD service;

  @PostMapping("/setSalary")
  protected ModelAndView service(HttpServletRequest req)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    ModelAndView modelAndView = new ModelAndView();
    BigDecimal newSalary = Checking.getSalary(req.getParameter(Parameters.SALARY));
    int id = Checking.getId(req.getParameter(Parameters.ID_TEACHER));
    Teacher teacher = service.getTeacherWithId(id);
    teacher.getSalary().add(newSalary);
    service.saveTeacher(teacher);
    session.setAttribute(Attributes.TEACHERS, service.showAllTeachers());
    modelAndView.setViewName("AdminSetSalary");
    return modelAndView;
  }

  @Autowired
  public void setService(ServiceCRUD service) {
    this.service = service;
  }
}
