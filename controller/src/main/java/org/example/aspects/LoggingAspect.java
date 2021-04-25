package org.example.aspects;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("execution(* org.example.controller.*.*(..))")
  public void controllers() {
  }

  @Before("controllers()")
  public void before(JoinPoint joinPoint) {
    log.error(joinPoint.toShortString());
    log.info("Args - " + Arrays.toString(joinPoint.getArgs()));
  }
}
