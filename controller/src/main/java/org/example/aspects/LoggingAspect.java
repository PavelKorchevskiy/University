package org.example.aspects;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("execution(* org.example.controller.*.*(..))")
  public void controllers() {
  }

  @Before("controllers()")
  public void logging(JoinPoint joinPoint) {
    log.error(joinPoint.toShortString());
    log.info("Args - " + Arrays.toString(joinPoint.getArgs()));
  }
}
