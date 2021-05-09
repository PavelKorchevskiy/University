package org.example.aspects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.constans.Attributes;
import org.example.constans.Role;
import org.example.exceptions.IllegalAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

  @Before("@annotation(AdminAccess)")
  public void checkRoleAdmin(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    HttpServletRequest request = (HttpServletRequest) args[0];
    HttpSession session = request.getSession();
    String role = (String) session.getAttribute(Attributes.ROLE);
    if (!role.equals(Role.ADMIN)) {
      throw new IllegalAccessException("You not a admin");
    }
  }

  @Before("@annotation(TeacherAccess)")
  public void checkRoleTeacher(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    HttpServletRequest request = (HttpServletRequest) args[0];
    HttpSession session = request.getSession();
    String role = (String) session.getAttribute(Attributes.ROLE);
    if (!role.equals(Role.TEACHER)) {
      throw new IllegalAccessException("You not a teacher");
    }
  }

}
