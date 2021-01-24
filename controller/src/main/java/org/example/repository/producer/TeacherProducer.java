package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.jdbc.RepositoryForTeacherJDBC;

public class TeacherProducer {

  public static RepositoryForTeachersInterface getRepository() {
    //return RepositoryForTeachersInMemory.getInstance();
    return RepositoryForTeacherJDBC.getInstance();
  }
}
