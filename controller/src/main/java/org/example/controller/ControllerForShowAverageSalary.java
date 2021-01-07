package org.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.service.AverageSalary;
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
    int numberOfMonths = 1;
    try {
      numberOfMonths = Integer.parseInt(req.getParameter("numberOfMonths"));
      log.info("number of months " + numberOfMonths);
    } catch (NumberFormatException e) {
      log.error("not a number in number of months");
      //нужно ли кидать исключение в таком случае?
      //пусть пока ни чего не происходит
    }
    session.setAttribute("averageSalary",
        AverageSalary.showAverageSalaryForAllTeacher(numberOfMonths));
    req.getRequestDispatcher("pages/AdminAverageSalary.jsp").forward(req, resp);
  }
}
