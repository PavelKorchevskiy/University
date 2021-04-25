package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RepositoryForTeacherHibernate implements RepositoryForTeachersInterface {

  private static volatile RepositoryForTeacherHibernate instance;

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

    Query query = HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Teacher where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    return query.list().stream().findFirst();

  }

  @Override
  public List<Teacher> findAll() {
    return (List<Teacher>) HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Teacher ").list();

  }

  @Override
  public Optional<Teacher> findById(int id) {
//    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
//    Criteria criteria = session.createCriteria(Teacher.class).add(Restrictions.eq("id", id));
//    List<Teacher> teachers = criteria.list();
//    return teachers.stream().findAny();

    Query query = HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Teacher where id = :id");
    query.setParameter("id", id);
    return query.stream().findAny();
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
    session.close();
    return teacher;
  }

  public Teacher update(Teacher teacher) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    //session.update(teacher);
    session.merge(teacher);
    transaction.commit();
    session.close();
    return teacher;
  }

  @Override
  public Teacher remove(Teacher teacher) {
    return null;
  }
}
