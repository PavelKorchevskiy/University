package org.example.repository.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.example.exceptions.IllegalFormatException;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.repository.jdbc.DataSource;
import org.example.subject.Subject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RepositoryForStudentHibernate implements RepositoryForStudentsInterface {

  private static volatile RepositoryForStudentHibernate instance;

  protected final EntityManagerHelper helper = EntityManagerHelper.getInstance();

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

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Student> query = (Query<Student>) session
        .createQuery("from Student where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    Optional<Student> any = query.stream().findAny();
    //session.close();
    return any;
  }

  @Override
  public List<Student> findAll() {
    List<Student> result;
    EntityManager em = helper.getEntityManager();
    EntityTransaction trx = em.getTransaction();
    trx.begin();
    result = (List<Student>) em.createQuery("from Student ").getResultList();
    trx.commit();
    //em.close();
    return result;
//    Session session = HibernateSessionFactory.getSessionFactory().openSession();
//    List<Student> students = (List<Student>) session.createQuery("from Student ").list();
//    return students;
  }

    @Override
    public Optional<Student> findById(int id) {

      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Query<Student> query = (Query<Student>) session.createQuery("from Student where id = :id");
      query.setParameter("id", id);
      Optional<Student> student = query.stream().findAny();
      //session.close();
      return student;
    }

    @Override
    public Student save(Student student) {
//      EntityManager em = helper.getEntityManager();
//      EntityTransaction trx = em.getTransaction();
//      trx.begin();
//      if(student.getId() == 0) {
//        em.persist(student);
//      } else {
//        em.merge(student);
//      }
//      trx.commit();
//      em.close();
//      return student;

      if (findAll().stream().map(Student::getId)
          .collect(Collectors.toList())
          .contains(student.getId())) {
        return update(student);
      }
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.save(student);
      transaction.commit();
      //session.close();
      return student;
    }

    public Student update(Student student) {
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(student);
      transaction.commit();
      //session.close();
      return student;
    }

  @Override
  public Student remove(Student student) {
    return null;
  }
}
