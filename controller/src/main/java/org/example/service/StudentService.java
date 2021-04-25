package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Student;
import org.example.subject.Subject;

@Slf4j
public class StudentService {

  public static synchronized Student putRating(Student student, Subject subject, int rating) {
    try {
      if (rating >= 0 && rating <= 100) {
        student.getRatings().put(subject, rating);
      }
    } catch (IllegalArgumentException e) {
      log.error("rating or subject is not valid");
    }
    return student;
  }

  public static synchronized String getRatingAsString(Student student) {
    StringBuffer sb = new StringBuffer();
    sb.append(student.getName()).append(" has rating: ");
    student.getRatings().forEach((k, v) -> sb.append(k).append(" - ").append(v).append(", "));
    sb.deleteCharAt(sb.lastIndexOf(","));
    return sb.toString();
  }
}
