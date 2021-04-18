package org.example.repository.producer;

import java.util.Map;
import org.example.repository.hibernate.RepositoryForGroupHibernate;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;
import org.example.repository.memory.RepositoryForGroupInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class GroupProducer {

 // @Autowired
  private Map<String, RepositoryForGroupInterface> map;

  //@Bean
//  public static RepositoryForGroupInterface getRepository() {
//    switch (RepositoryType.type) {
//      case "memory":
//        return RepositoryForGroupInMemory.getInstance();
//      case "jdbc":
//        return RepositoryForGroupJDBC.getInstance();
//      default:
//        return RepositoryForGroupHibernate.getInstance();
//    }
//  }
}
