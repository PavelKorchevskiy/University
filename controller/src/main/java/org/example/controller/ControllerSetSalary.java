package org.example.controller;

import org.example.model.Teacher;
import org.example.repository.RepositoryForTeachersInMemory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet("/setSalary")
public class ControllerSetSalary extends HttpServlet {

    //админ выдает учителю одну зарплату
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        BigDecimal newSalary = new BigDecimal(0);
        try {
            newSalary = BigDecimal.valueOf(Double.parseDouble(req.getParameter("salaryTeacher")));
        } catch (NumberFormatException e) {
            //нужно ли кидать исключение в таком случае?
            //пусть пока ни чего не происходит
        }
        String login = req.getParameter("loginTeacher");
        Optional<Teacher> optionalTeacher = RepositoryForTeachersInMemory.getInstance().findByLogin(login);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacher.getSalary().add(newSalary);
        }
        req.getRequestDispatcher("WEB-INF/pages/AdminSetSalary.jsp").forward(req, resp);
    }
}
