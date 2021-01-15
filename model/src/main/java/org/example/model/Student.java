package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.example.group.Group;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student extends AbstractPerson {

  private static final Logger log = LoggerFactory.getLogger(Student.class);
  private final Map<Subject, Integer> ratings = new HashMap<>();

  public Student(int id, String login, String password, String fullName, int age, Set<Subject> subjects) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setFullName("Petia");
    }
    //student start with rating 0
    for (Subject s : subjects) {
      ratings.put(s, 0);
    }
  }

  public Student(int id, String login, String password, String fullName, int age) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setFullName("Petia");
    }
  }

//учитель может добавить предмет и рейтинг
  public void putRating(Subject subject, int rating) {
    try {
      if (rating >= 0 && rating <= 100) {
        ratings.put(subject, rating);
      }
    } catch (IllegalArgumentException e) {
      log.error("rating or subject is not valid");
    }
  }

  public String getRatingAsString() {
    StringBuffer sb = new StringBuffer();
    sb.append(getFullName()).append(" has rating: ");
    ratings.forEach((k, v) -> sb.append(k).append(" - ").append(v).append(", "));
    sb.deleteCharAt(sb.lastIndexOf(","));
    return sb.toString();
  }

  public Map<Subject, Integer> getRatings() {
    return ratings;
  }
}
