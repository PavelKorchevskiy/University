package org.example.model;

import org.example.subject.Subject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Student extends AbstractPerson {

  private Set<Subject> subjects = new HashSet<>();
  private Map<Subject, Integer> ratings = new HashMap<>();

  public Student(String login, String password, String fullName, int age, Set<Subject> subjects) {
    super(login, password, fullName, age);
    this.subjects = subjects;
    //student start with rating 0
    for (Subject s : subjects) {
      ratings.put(s, 0);
    }
  }

  public void putRating(Subject subject, int rating) {
    //TODO: throw exception
    if (rating >= 0 && rating <= 100) {
      ratings.put(subject, rating);
    }
  }

//  перегруженный метод, принимающий строку
  public void putRating(String subject, int rating) {
    //TODO: throw exception
    try {
      Subject s = Subject.valueOf(subject);
      if (rating >= 0 && rating <= 100) {
        ratings.put(s, rating);
      }
    } catch (IllegalArgumentException e) {
      //нужно ли кидать исключение в таком случае?
      //пусть пока ни чего не происходит
    }
  }

  public void deleteRating(Subject subject) {
    ratings.remove(subject);
  }

  public String getRatingAsString() {
    StringBuilder sb = new StringBuilder();
    ratings.forEach((k,v) -> sb.append(k).append(" - ").append(v).append(", "));
    sb.deleteCharAt(sb.lastIndexOf(","));
    return getFullName() + " has rating: " + sb.toString();
  }
}
