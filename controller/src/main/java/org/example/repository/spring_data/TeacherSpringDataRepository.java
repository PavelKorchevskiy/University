package org.example.repository.spring_data;

import java.util.List;
import java.util.Optional;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TeacherSpringDataRepository extends CrudRepository<Teacher, Integer>,
    RepositoryForTeachersInterface {

  @Override
  List<Teacher> findAll();

  Optional<Teacher> findById(int id);

  Optional<Teacher> findByLoginAndPassword(String login, String password);
}
