package org.example.repository.spring_jpa;

import org.example.group.Group;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.springframework.stereotype.Repository;

@Repository("springJpaG")
public class GroupSpringJpaRepository extends AbstractSpringJpaRepository<Group> implements
    RepositoryForGroupInterface {

  public GroupSpringJpaRepository() {
    clazz = Group.class;
  }
}
