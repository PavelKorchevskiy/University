package org.example.filtres;

import org.example.model.Admin;
import org.example.repository.RepositoryForStudentsInMemory;
import org.example.repository.RepositoryForStudentsInterface;
import org.example.repository.RepositoryForTeachersInMemory;
import org.example.repository.RepositoryForTeachersInterface;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        String role;
        if (nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
            role = (String) session.getAttribute("role");
        } else if (nonNull(login) && nonNull(password)) {
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            role = getAccess(login, password);
            session.setAttribute("role", role);
        } else {
            role = "no";
            req.getRequestDispatcher("LoginPage.jsp").forward(req, resp);
        }
        goToPage(role, req, resp);
        //нужно ли?
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
    //определяем доступ для введенного логина и пароля
    private String getAccess(String login, String password) {
        String access = "no";
        RepositoryForTeachersInterface repositoryForTeachers = RepositoryForTeachersInMemory.getInstance();
        RepositoryForStudentsInterface repositoryForStudents = RepositoryForStudentsInMemory.getInstance();

        if (repositoryForTeachers.findByLoginAndPassword(login, password).isPresent()) {
            access = "teacher";
        } else if (repositoryForStudents.findByLoginAndPassword(login, password).isPresent()) {
            access = "student";
        } else if (login.equals(Admin.getInstance().getLogin()) && Admin.getInstance().getPassword().equals(password)) {
            access = "admin";
        }
        return access;
    }

    private void goToPage(String role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (role) {
            case "admin":
                req.getRequestDispatcher("WEB-INF/pages/AdminPage.jsp").forward(req, resp);
                break;
            case "student":
                req.getRequestDispatcher("WEB-INF/pages/StudentPage.jsp").forward(req, resp);
                break;
            case "teacher":
                req.getRequestDispatcher("WEB-INF/pages/TeacherPage.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("WEB-INF/pages/AdminPage.jsp").forward(req, resp);
                break;
        }
    }
}
