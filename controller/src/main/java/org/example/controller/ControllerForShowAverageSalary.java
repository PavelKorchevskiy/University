package org.example.controller;

import org.example.service.AverageSalary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/showSalary")
public class ControllerForShowAverageSalary extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //при неподходящем числе выведем информацию за один месяц
        int numberOfMonths = 1;
        try {
            numberOfMonths = Integer.parseInt(req.getParameter("numberOfMonths"));
        } catch (NumberFormatException e) {
            //нужно ли кидать исключение в таком случае?
            //пусть пока ни чего не происходит
        }
        session.setAttribute("averageSalary", AverageSalary.showAverageSalaryForAllTeacher(numberOfMonths));
        req.getRequestDispatcher("WEB-INF/pages/AdminAverageSalary.jsp").forward(req, resp);
    }
}
