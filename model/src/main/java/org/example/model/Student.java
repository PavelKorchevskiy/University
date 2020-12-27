package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student extends AbstractPerson {

  private static final Logger log = LoggerFactory.getLogger(Student.class);
  private final Map<Subject, Integer> ratings = new HashMap<>();

  public Student(String login, String password, String fullName, int age, Set<Subject> subjects) {
    super(login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setFullName("Petia");
    }
    //student start with rating 0
    for (Subject s : subjects) {
      ratings.put(s, 0);
    }
  }

  public void putRating(String subject, int rating) {
    try {
      Subject s = Subject.valueOf(subject);
      if (rating >= 0 && rating <= 100) {
        ratings.put(s, rating);
      }
    } catch (IllegalArgumentException e) {
      log.error("rating is not valid");
    }
  }

  public String getRatingAsString() {
    StringBuilder sb = new StringBuilder();
    ratings.forEach((k, v) -> sb.append(k).append(" - ").append(v).append(", "));
    sb.deleteCharAt(sb.lastIndexOf(","));
    return getFullName() + " has rating: " + sb.toString();
  }
}
