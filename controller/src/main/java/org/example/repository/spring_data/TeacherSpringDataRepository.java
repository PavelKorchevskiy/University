package org.example.repository.spring_data;

import java.util.List;
import java.util.Optional;
import org.example.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherSpringDataRepository extends CrudRepository<Teacher, Integer> {

  @Override
  List<Teacher> findAll();

  Optional<Teacher> findById(int id);

  Optional<Teacher> findByLoginAndPassword(String login, String password);
}
