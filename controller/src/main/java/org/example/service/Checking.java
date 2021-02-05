package org.example.service;

import java.math.BigDecimal;
import org.example.constans.Parameters;
import org.example.controller.ControllerForChangeRating;
import org.example.excetions.IllegalDataException;
import org.example.excetions.IllegalFormatException;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checking {

  private static final Logger log = LoggerFactory.getLogger(Checking.class);

  public static int getId(String parameter) {
    int id;
    try {
      id = Integer.parseInt(parameter);
      log.info("id - " + id);
    } catch (NumberFormatException e) {
      log.error("id is not a number");
      throw new IllegalFormatException("id is not a number");
    }
    return id;
  }

  public static Subject getSubject(String parameter) {
    Subject subject;
    try {
      subject = Subject.getSubjectByString(parameter);
    } catch (IllegalArgumentException e) {
      log.info(String.format("subject is not correct - %s", parameter));
      throw new IllegalFormatException("subject is not correct - " + parameter);
    }
    return subject;
  }

  public static int getRating(String parameter) {
    int rating;
    try {
      rating = Integer.parseInt(parameter);
      log.info(String.valueOf(rating));
    } catch (NumberFormatException e) {
      log.error("not a number in rating");
      throw new IllegalFormatException("not a number in rating");
    }
    if (rating > 100 || rating < 0) {
      throw new IllegalDataException("rating less than 0 or more than 100");
    }
    return rating;
  }

  public static BigDecimal getSalary(String parameter) {
    BigDecimal newSalary;
    try {
      newSalary = BigDecimal.valueOf(Double.parseDouble(parameter));
      log.info("new salary - " + newSalary);
    } catch (NumberFormatException e) {
      log.error("salary is not a number");
      throw new IllegalFormatException("salary is not a number");
    }
    return newSalary;
  }

}
