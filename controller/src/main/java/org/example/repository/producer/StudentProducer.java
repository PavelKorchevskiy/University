package org.example.repository.producer;

import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.jdbc.RepositoryForStudentJDBC;

public class StudentProducer {

  public static RepositoryForStudentsInterface getRepository() {
    //return RepositoryForStudentsInMemory.getInstance();
    return RepositoryForStudentJDBC.getInstance();
  }
}
