package org.example.filters;

import static java.util.Objects.nonNull;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.example.constans.Attributes;
import org.example.model.Admin;
import org.example.repository.RepositoryForStudentsInMemory;
import org.example.repository.RepositoryForStudentsInterface;
import org.example.repository.RepositoryForTeachersInMemory;
import org.example.repository.RepositoryForTeachersInterface;
@WebInitParam(name = "AdminLogin", value = "admin")

public class AuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String login = req.getParameter("login");
    String password = req.getParameter("password");

    HttpSession session = req.getSession();
    String role;
    if (nonNull(session.getAttribute(Attributes.LOGIN)) && nonNull(session.getAttribute(Attributes.PASSWORD))) {
      role = (String) session.getAttribute(Attributes.ROLE);
    } else if (nonNull(login) && nonNull(password)) {
      session.setAttribute(Attributes.LOGIN, login);
      session.setAttribute(Attributes.PASSWORD, password);
      role = getAccess(login, password);
      session.setAttribute(Attributes.ROLE, role);
    } else {
      role = "no";
      req.getRequestDispatcher("pages/LoginPage.jsp").forward(req, resp);
    }
    goToPage(role, req, resp);
  }

  @Override
  public void destroy() {
  }

  //определяем доступ для введенного логина и пароля
  private String getAccess(String login, String password) {
    String access = "no";
    //момент создания репозиториев, сначала создается репозиторий для учителей
    RepositoryForTeachersInterface repositoryForTeachers = RepositoryForTeachersInMemory
        .getInstance();
    RepositoryForStudentsInterface repositoryForStudents = RepositoryForStudentsInMemory
        .getInstance();

    if (repositoryForTeachers.findByLoginAndPassword(login, password).isPresent()) {
      access = "teacher";
    } else if (repositoryForStudents.findByLoginAndPassword(login, password).isPresent()) {
      access = "student";
    } else if (login.equals(Admin.getInstance().getLogin()) && Admin.getInstance().getPassword()
        .equals(password)) {
      access = "admin";
    }
    return access;
  }

  private void goToPage(String role, HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    switch (role) {
      case "admin":
        req.getRequestDispatcher("pages/AdminPage.jsp").forward(req, resp);
        break;
      case "student":
        req.getRequestDispatcher("pages/StudentPage.jsp").forward(req, resp);
        break;
      case "teacher":
        req.getRequestDispatcher("pages/TeacherPage.jsp").forward(req, resp);
        break;
      default:
        final HttpSession session = req.getSession();
        session.removeAttribute(Attributes.PASSWORD);
        session.removeAttribute(Attributes.LOGIN);
        session.removeAttribute(Attributes.ROLE);
        req.getRequestDispatcher("pages/LoginPage.jsp").forward(req, resp);
        break;
    }
  }

  @Override
  public void init(FilterConfig filterConfig) {
    String login = filterConfig.getInitParameter("AdminLogin");
    Admin.getInstance().setLogin(login);
    Admin.getInstance().setPassword(login);
  }
}
