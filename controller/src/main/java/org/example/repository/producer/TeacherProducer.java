package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.jdbc.RepositoryForTeacherJDBC;
import org.example.repository.memory.RepositoryForTeachersInMemory;

public class TeacherProducer {

  public static RepositoryForTeachersInterface getRepository() {
    switch (RepositoryType.type) {
      case "memory":
        return RepositoryForTeachersInMemory.getInstance();
      default:
        return RepositoryForTeacherJDBC.getInstance();
    }
  }
}
