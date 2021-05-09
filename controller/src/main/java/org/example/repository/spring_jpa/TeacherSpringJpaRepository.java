package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.springframework.stereotype.Repository;

@Repository("springJpaT")
public class TeacherSpringJpaRepository extends AbstractSpringJpaRepository<Teacher> implements
    RepositoryForTeachersInterface {

  public TeacherSpringJpaRepository() {
    clazz = Teacher.class;
  }

  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {

    TypedQuery<Teacher> query = entityManager
        .createQuery("from " + clazz.getName() + " where login = :login and password = :password",
            clazz);
    query.setParameter("login", login);
    query.setParameter("password", password);
    List<Teacher> list = query.getResultList();
    return list.stream().findAny();
  }

}
