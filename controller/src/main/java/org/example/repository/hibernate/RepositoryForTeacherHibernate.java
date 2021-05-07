package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository("hibernateT")
public class RepositoryForTeacherHibernate implements RepositoryForTeachersInterface {

  protected final EntityManagerHelper helper = EntityManagerHelper.getInstance();


  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Teacher> query = (Query<Teacher>) session
        .createQuery("from Teacher where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    Optional<Teacher> first = query.list().stream().findFirst();
    return first;
  }

  @Override
  public List<Teacher> findAll() {
    List<Teacher> result;
    EntityManager em = helper.getEntityManager();
    EntityTransaction trx = em.getTransaction();
    trx.begin();
    result = (List<Teacher>) em.createQuery("from " + Teacher.class.getName()).getResultList();
    trx.commit();
    return result;
  }

  @Override
  public Optional<Teacher> findById(int id) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Teacher> query = (Query<Teacher>) session
        .createQuery("from Teacher where id = :id");
    query.setParameter("id", id);
    Optional<Teacher> any = query.stream().findAny();
    return any;
  }

  @Override
  public Teacher save(Teacher teacher) {
    if (findAll().stream().map(Teacher::getId).collect(Collectors.toList())
        .contains(teacher.getId())) {
      return update(teacher);
    }
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.save(teacher);
    transaction.commit();
    return teacher;
  }

  public Teacher update(Teacher teacher) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.merge(teacher);
    transaction.commit();
    return teacher;
  }
}
