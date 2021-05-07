package org.example.repository.spring_data;

import java.util.List;
import java.util.Optional;
import org.example.group.Group;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface GroupSpringDataRepository extends CrudRepository<Group, Integer>,
    RepositoryForGroupInterface {

  @Override
  List<Group> findAll();

  @Override
  Optional<Group> findById(Integer integer);
}
