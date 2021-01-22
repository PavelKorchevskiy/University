package org.example.repository.interfaces;

import java.util.Optional;
import org.example.model.Teacher;

public interface RepositoryForTeachersInterface extends RepositoryInterface<Teacher> {

  Optional<Teacher> findByLoginAndPassword(String login, String password);
}
