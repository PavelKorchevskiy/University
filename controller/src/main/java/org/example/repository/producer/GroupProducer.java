package org.example.repository.producer;

import org.example.repository.hibernate.RepositoryForGroupHibernate;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;
import org.example.repository.memory.RepositoryForGroupInMemory;
import org.springframework.context.annotation.Bean;

public class GroupProducer {

  @Bean
  public static RepositoryForGroupInterface getRepository() {
    switch (RepositoryType.type) {
      case "memory":
        return RepositoryForGroupInMemory.getInstance();
      case "jdbc":
        return RepositoryForGroupJDBC.getInstance();
      default:
        return RepositoryForGroupHibernate.getInstance();
    }
  }
}
