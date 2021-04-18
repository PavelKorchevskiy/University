package org.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.constans.Attributes;
import org.example.constans.Parameters;
import org.example.service.Checking;
import org.example.service.ServiceCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerForShowAverageSalary {

  private ServiceCRUD service;

  @PostMapping("/showSalary")
  protected ModelAndView service(HttpServletRequest req)
      throws ServletException, IOException {
    ModelAndView modelAndView = new ModelAndView();
    HttpSession session = req.getSession();
    //при неподходящем числе выведем информацию за один месяц
    int numberOfMonths = Checking.getNumber(req.getParameter(Parameters.NUMB_MONTHS));
    session.setAttribute(Attributes.AVERAGE_SALARY,
        service.showAverageSalaryForAllTeacher(numberOfMonths));
    modelAndView.setViewName("AdminAverageSalary");
    return modelAndView;
  }

  @Autowired
  public void setService(ServiceCRUD service) {
    this.service = service;
  }
}
