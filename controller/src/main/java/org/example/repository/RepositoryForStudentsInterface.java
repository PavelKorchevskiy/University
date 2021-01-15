package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.model.Student;

public interface RepositoryForStudentsInterface extends RepositoryInterface<Student> {

  List<Student> findAll();

  Optional<Student> findById(int id);

  Student save(Student student);

  Student remove(Student student);

  Optional<Student> findByLoginAndPassword(String login, String password);
}
