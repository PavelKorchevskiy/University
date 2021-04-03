package org.example.repository.producer;

import org.example.repository.hibernate.RepositoryForStudentHibernate;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.jdbc.RepositoryForStudentJDBC;
import org.example.repository.memory.RepositoryForStudentsInMemory;
import org.springframework.context.annotation.Bean;

public class StudentProducer {

  @Bean
  public static RepositoryForStudentsInterface getRepository() {
    switch (RepositoryType.type) {
      case "memory":
        return RepositoryForStudentsInMemory.getInstance();
      case "jdbc":
        return RepositoryForStudentJDBC.getInstance();
      default:
        return RepositoryForStudentHibernate.getInstance();
    }
  }
}
