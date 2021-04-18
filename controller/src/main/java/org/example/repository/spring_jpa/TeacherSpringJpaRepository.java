package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
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
    begin();
    Query query = getEntityManager()
        .createQuery("from Teacher where login = :login and password = :password", clazz);
    query.setParameter("login", login);
    query.setParameter("password", password);
    List<Teacher> list = (List<Teacher>) query.getResultList();
    commit();
    return list.stream().findAny();
  }

}
