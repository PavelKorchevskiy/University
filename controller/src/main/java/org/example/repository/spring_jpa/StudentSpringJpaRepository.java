package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
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
    begin();
    Query query = getEntityManager()
        .createQuery("from Student where login = :login and password = :password", clazz);
    query.setParameter("login", login);
    query.setParameter("password", password);
    List<Student> list = (List<Student>) query.getResultList();
    commit();
    return list.stream().findAny();
  }
}
