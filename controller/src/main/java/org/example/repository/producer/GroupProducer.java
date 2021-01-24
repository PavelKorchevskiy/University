package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.example.repository.jdbc.RepositoryForGroupJDBC;

public class GroupProducer {

  public static RepositoryForGroupInterface getRepository() {
    //return RepositoryForGroupInMemory.getInstance();
    return RepositoryForGroupJDBC.getInstance();
  }
}
