package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.springframework.stereotype.Repository;

@Repository("springJpaS")
public class StudentSpringJpaRepository extends AbstractSpringJpaRepository<Student> implements
    RepositoryForStudentsInterface {

  public StudentSpringJpaRepository() {
    clazz = Student.class;
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    TypedQuery<Student> query = entityManager
        .createQuery("from " + clazz.getName() + " where login = :login and password = :password",
            clazz);
    query.setParameter("login", login);
    query.setParameter("password", password);
    List<Student> list = query.getResultList();
    return list.stream().findAny();
  }
}
