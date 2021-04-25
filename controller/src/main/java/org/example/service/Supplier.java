package org.example.service;

import org.example.exceptions.IllegalDataException;
import org.example.model.Teacher;
import org.example.repository.producer.TeacherProducer;

public class Supplier {

  public static Teacher getTeacherWithLoginAngPassword(String login, String password) {
    return TeacherProducer.getRepository().findByLoginAndPassword(login, password)
        .orElseThrow(
            () -> new IllegalDataException("Teacher with this login and password doesn't exist"));
  }

  public static Teacher getTeacherWithId(int id) {
    return TeacherProducer.getRepository().findById(id)
        .orElseThrow(
            () -> new IllegalDataException("Teacher with id - " + id + " doesn't exist"));
  }

}

