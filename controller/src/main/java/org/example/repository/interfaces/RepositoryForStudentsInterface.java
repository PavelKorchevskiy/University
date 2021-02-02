package org.example.repository.interfaces;

import java.util.Optional;
import org.example.model.Student;

public interface RepositoryForStudentsInterface extends RepositoryInterface<Student> {

  Optional<Student> findByLoginAndPassword(String login, String password);
}
