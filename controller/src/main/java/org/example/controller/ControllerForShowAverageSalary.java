package org.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.constans.Attributes;
import org.example.constans.Parameters;
import org.example.service.AverageSalary;
import org.example.service.Checking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/showSalary")
public class ControllerForShowAverageSalary extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(ControllerForShowAverageSalary.class);

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    //при неподходящем числе выведем информацию за один месяц
    int numberOfMonths = Checking.getNumber(req.getParameter(Parameters.NUMB_MONTHS));
    session.setAttribute(Attributes.AVERAGE_SALARY,
        AverageSalary.showAverageSalaryForAllTeacher(numberOfMonths));
    req.getRequestDispatcher("pages/AdminAverageSalary.jsp").forward(req, resp);
  }
}
