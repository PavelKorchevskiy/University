package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RepositoryForStudentHibernate implements RepositoryForStudentsInterface {

  private static volatile RepositoryForStudentHibernate instance;

  private RepositoryForStudentHibernate() {
  }

  public static RepositoryForStudentHibernate getInstance() {
    if (instance == null) {
      synchronized (RepositoryForStudentHibernate.class) {
        if (instance == null) {
          instance = new RepositoryForStudentHibernate();
        }
      }
    }
    return instance;
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
//    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
//    Criteria criteria = session.createCriteria(Student.class).add(Restrictions.eq("login", login))
//        .add(Restrictions.eq("password", password));
//    List<Student> teachers = criteria.list();
//    return teachers.stream().findAny();

    Query<Student> query = HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Student where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    return query.stream().findAny();
  }

  @Override
  public List<Student> findAll() {
    return (List<Student>) HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Student ").list();
  }

  @Override
  public Optional<Student> findById(int id) {
//    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
//    Criteria criteria = session.createCriteria(Student.class).add(Restrictions.eq("id", id));
//    List<Student> teachers = criteria.list();
//    return teachers.stream().findAny();

    Query<Student> query = HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Student where id = :id");
    query.setParameter("id", id);
    return query.stream().findAny();
  }

  @Override
  public Student save(Student student) {
    if (findAll().stream().map(Student::getId).collect(Collectors.toList())
        .contains(student.getId())) {
      return update(student);
    }
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.save(student);
    transaction.commit();
    session.close();
    return student;
  }

  public Student update(Student student) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.merge(student);
    transaction.commit();
    session.close();
    return student;
  }

  @Override
  public Student remove(Student student) {
    return null;
  }
}
