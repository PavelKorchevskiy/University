package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.model.Teacher;

public interface RepositoryForTeachersInterface extends RepositoryInterface<Teacher> {

  List<Teacher> findAll();

  Optional<Teacher> findById(int id);

  Teacher save(Teacher teacher);

  Teacher remove(Teacher teacher);

  Optional<Teacher> findByLoginAndPassword(String login, String password);
}
