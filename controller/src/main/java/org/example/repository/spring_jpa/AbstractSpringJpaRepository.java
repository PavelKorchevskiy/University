package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.example.repository.interfaces.RepositoryInterface;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractSpringJpaRepository<T> implements RepositoryInterface<T> {

  protected Class<T> clazz;

  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  protected EntityManager entityManager;

  @Override
  public List<T> findAll() {
    return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
  }

  @Override
  public Optional<T> findById(int id) {
    return Optional.of(entityManager.find(clazz, id));
  }


  @Override
  public <S extends T> S save(S s) {
    entityManager.merge(s);
    return s;
  }

  public T remove(T t) {
    List<T> list = findAll();
    if (list.contains(t)) {
      entityManager.remove(t);
      return t;
    }
    return null;
  }
}
