package org.example.repository.spring_data;

import java.util.List;
import java.util.Optional;
import org.example.group.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupSpringDataRepository extends CrudRepository<Group, Integer> {

  @Override
  List<Group> findAll();

  @Override
  Optional<Group> findById(Integer integer);
}
