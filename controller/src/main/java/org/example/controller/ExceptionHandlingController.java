package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import org.example.exceptions.AppException;
import org.example.exceptions.IllegalAccessException;
import org.example.exceptions.IllegalDataException;
import org.example.exceptions.IllegalFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlingController {

  @ExceptionHandler({IllegalAccessException.class, IllegalDataException.class,
      IllegalFormatException.class})
  public ModelAndView customExceptionHandle(HttpServletRequest req, Exception ex) {
    return new ModelAndView("ExceptionPage");
  }

}
