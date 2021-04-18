package org.example.repository.spring_jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractSpringJpaRepository<T> implements RepositoryInterface<T> {

  protected Class<T> clazz;
  private final ThreadLocal<EntityManager> em = new ThreadLocal<>();
  private EntityManagerFactory factory;

  @Autowired
  public void setFactory(EntityManagerFactory factory) {
    this.factory = factory;
  }

  @Override
  public List<T> findAll() {
    begin();
    List<T> resultList = getEntityManager().createQuery("from " + clazz.getName(), clazz).getResultList();
    commit();
    return resultList;
  }

  @Override
  public Optional<T> findById(int id) {
    return Optional.of(getEntityManager().find(clazz, id));
  }


  @Override
  public T save(T t) {
    begin();
    getEntityManager().persist(t);
    commit();
    return t;
  }

  @Override
  public T remove(T t) {
    begin();
    List<T> list = findAll();
    if (list.contains(t)) {
      getEntityManager().remove(t);
      commit();
      return t;
    }
    commit();
    return null;
  }

  public EntityManager getEntityManager() {
    if (em.get() == null) {
      em.set(factory.createEntityManager());
    }
    return em.get();
  }

  public void begin() {
    getEntityManager().getTransaction().begin();
  }

  public void commit() {
    getEntityManager().getTransaction().commit();
  }

  public void rollback() {
    getEntityManager().getTransaction().rollback();
  }
}
