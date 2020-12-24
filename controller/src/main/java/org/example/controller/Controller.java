package org.example.controller;

import org.example.service.AverageSalary;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class Controller extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int countOfMonth = Integer.parseInt(req.getParameter("count"));
    AverageSalary service = new AverageSalary();
    String report = service.report(countOfMonth);
    HttpSession session = req.getSession();
    session.setAttribute("report", report);
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/result");
    requestDispatcher.forward(req, resp);
  }
}
