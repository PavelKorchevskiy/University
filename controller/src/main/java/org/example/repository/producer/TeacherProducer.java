package org.example.repository.producer;

import org.example.repository.hibernate.RepositoryForTeacherHibernate;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.example.repository.jdbc.RepositoryForTeacherJDBC;
import org.example.repository.memory.RepositoryForTeachersInMemory;
import org.springframework.context.annotation.Bean;

public class TeacherProducer {

  @Bean
  public static RepositoryForTeachersInterface getRepository() {
    switch (RepositoryType.type) {
      case "memory":
        return RepositoryForTeachersInMemory.getInstance();
      case "jdbc":
        return RepositoryForTeacherJDBC.getInstance();
      default:
        return RepositoryForTeacherHibernate.getInstance();
    }
  }
}
