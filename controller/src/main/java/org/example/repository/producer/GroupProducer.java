package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;
import org.example.repository.memory.RepositoryForGroupInMemory;

public class GroupProducer {

  public static RepositoryForGroupInterface getRepository() {
    switch (RepositoryType.type) {
      case "memory":
        return RepositoryForGroupInMemory.getInstance();
      default:
        return RepositoryForGroupJDBC.getInstance();
    }
  }
}
