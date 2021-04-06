package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.jdbc.RepositoryForStudentJDBC;
import org.example.repository.producer.StudentProducer;
import org.example.subject.Subject;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class StudentServ {



  public Student putRating(Student student, Subject subject, int rating) {
    try {
      if (rating >= 0 && rating <= 100) {
        student.getRatings().put(subject, rating);
      }
    } catch (IllegalArgumentException e) {
      log.error("rating or subject is not valid");
    }
    return student;
  }

}
