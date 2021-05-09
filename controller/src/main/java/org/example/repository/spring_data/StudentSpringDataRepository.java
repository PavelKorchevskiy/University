package org.example.repository.spring_data;

import java.util.List;
import java.util.Optional;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface StudentSpringDataRepository extends CrudRepository<Student, Integer>,
    RepositoryForStudentsInterface {

  @Override
  <S extends Student> S save(S s);

  @Override
  List<Student> findAll();

  Optional<Student> findByLoginAndPassword(String login, String password);

  Optional<Student> findById(int id);
}
