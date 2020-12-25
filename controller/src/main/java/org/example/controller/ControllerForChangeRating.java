package org.example.controller;

import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.RepositoryForTeachersInMemory;
import org.example.repository.RepositoryForTeachersInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/changeRating")
public class ControllerForChangeRating extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int rating = 0;
        try {
            rating = Integer.parseInt(req.getParameter("ratingStudent"));
        } catch (NumberFormatException e) {
            //нужно ли кидать исключение в таком случае?
            //пусть пока ни чего не происходит
        }
        String subject = req.getParameter("subjectStudent");
        String login = req.getParameter("loginStudent");
        RepositoryForTeachersInterface repository = RepositoryForTeachersInMemory.getInstance();
        Optional<Teacher> teacherOptional = repository.findByLogin(String.valueOf(session.getAttribute("login")));
        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            Optional<Student> studentOptional = teacher.getStudentByLogin(login);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                student.putRating(subject, rating);
            }
        }
        req.getRequestDispatcher("WEB-INF/pages/TeacherPage.jsp").forward(req, resp);
    }
}
