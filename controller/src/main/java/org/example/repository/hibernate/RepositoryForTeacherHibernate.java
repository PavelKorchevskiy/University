package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.example.exceptions.IllegalFormatException;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RepositoryForTeacherHibernate implements RepositoryForTeachersInterface {

  private static volatile RepositoryForTeacherHibernate instance;
  protected final EntityManagerHelper helper = EntityManagerHelper.getInstance();


  private RepositoryForTeacherHibernate() {
  }

  public static RepositoryForTeacherHibernate getInstance() {
    if (instance == null) {
      synchronized (RepositoryForTeacherHibernate.class) {
        if (instance == null) {
          instance = new RepositoryForTeacherHibernate();
        }
      }
    }
    return instance;
  }

  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {
//    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
//    Criteria criteria = session.createCriteria(Teacher.class).add(Restrictions.eq("login", login))
//        .add(Restrictions.eq("password", password));
//    List<Teacher> teachers = criteria.list();
//    return teachers.stream().findAny();

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Teacher> query = (Query<Teacher>) session
        .createQuery("from Teacher where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    Optional<Teacher> first = query.list().stream().findFirst();
    //session.close();
    return first;

  }

  @Override
  public List<Teacher> findAll() {
    List<Teacher> result;
    EntityManager em = helper.getEntityManager();
    EntityTransaction trx = em.getTransaction();
    trx.begin();
    result = (List<Teacher>) em.createQuery("from Teacher ").getResultList();
    trx.commit();
    //em.close();
    return result;
  }

  @Override
  public Optional<Teacher> findById(int id) {
//    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
//    Criteria criteria = session.createCriteria(Teacher.class).add(Restrictions.eq("id", id));
//    List<Teacher> teachers = criteria.list();
//    return teachers.stream().findAny();

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Teacher> query = (Query<Teacher>) session
        .createQuery("from Teacher where id = :id");
    query.setParameter("id", id);
    Optional<Teacher> any = query.stream().findAny();
    //session.close();
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
    //session.close();
    return teacher;
  }

  public Teacher update(Teacher teacher) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    //session.update(teacher);
    session.merge(teacher);
    transaction.commit();
    //session.close();
    return teacher;
  }

  @Override
  public Teacher remove(Teacher teacher) {
    return null;
  }
}
