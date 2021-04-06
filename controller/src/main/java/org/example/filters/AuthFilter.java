package org.example.filters;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.constans.Attributes;
import org.example.constans.Parameters;
import org.example.model.Admin;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.producer.StudentProducer;
import org.example.repository.producer.TeacherProducer;
import org.example.service.StudentService;
import org.example.service.TeachersServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebInitParam(name = "AdminLogin", value = "admin")
public class AuthFilter implements Filter {


  private final TeachersServ service = new TeachersServ();
  private final Logger log = LoggerFactory.getLogger(AuthFilter.class);


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    log.error("Start filter");
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    String login = req.getParameter(Parameters.LOGIN);
    String password = req.getParameter(Parameters.PASSWORD);
    HttpSession session = req.getSession();
    String role;
    if (nonNull(session.getAttribute(Attributes.LOGIN)) &&
        nonNull(session.getAttribute(Attributes.PASSWORD)) &&
        nonNull(session.getAttribute(Attributes.ROLE))) {
      role = (String) session.getAttribute(Attributes.ROLE);
    } else if (nonNull(login) && nonNull(password)) {
      session.setAttribute(Attributes.LOGIN, login);
      session.setAttribute(Attributes.PASSWORD, password);
      role = getAccess(login, password, session);
      session.setAttribute(Attributes.ROLE, role);
    } else {
      role = "no";
      req.getRequestDispatcher("pages/LoginPage.jsp").forward(req, resp);
    }
    if (role.equals("admin")) {
      session.setAttribute("allTeachers", service.showAllTeachers());
    }
    goToPage(role, req, resp);
  }

  @Override
  public void destroy() {
  }

  //определяем доступ для введенного логина и пароля
  private String getAccess(String login, String password, HttpSession session) {
    String access = "no";

    Optional<Student> optionalStudent = service.getStudentByLoginAndPassword(login, password);
    Optional<Teacher> optionalTeacher = service.getTeacherByLoginAndPassword(login, password);



    if (optionalTeacher.isPresent()) {
      access = "teacher";
      session.setAttribute(Attributes.GROUP, service.showGroup(optionalTeacher.get()));
    } else if (optionalStudent.isPresent()) {
      access = "student";
      session.setAttribute(Attributes.RATING
              , StudentService.getRatingAsString(optionalStudent.get()));
    } else if (login.equals(Admin.getInstance().getLogin())
        && Admin.getInstance().getPassword()
        .equals(password)) {
      access = "admin";
      session.setAttribute(Attributes.TEACHERS, service.showAllTeachers());
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
