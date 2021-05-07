package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository("hibernateS")
public class RepositoryForStudentHibernate implements RepositoryForStudentsInterface {

  protected final EntityManagerHelper helper = EntityManagerHelper.getInstance();

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Student> query = (Query<Student>) session
        .createQuery("from Student where login = :login and password = :password");
    query.setParameter("login", login);
    query.setParameter("password", password);
    Optional<Student> any = query.stream().findAny();
    return any;
  }

  @Override
  public List<Student> findAll() {
    List<Student> result;
    EntityManager em = helper.getEntityManager();
    EntityTransaction trx = em.getTransaction();
    trx.begin();
    result = (List<Student>) em.createQuery("from " + Student.class.getName()).getResultList();
    trx.commit();
    return result;
  }

    @Override
    public Optional<Student> findById(int id) {
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Query<Student> query = (Query<Student>) session.createQuery("from Student where id = :id");
      query.setParameter("id", id);
      Optional<Student> student = query.stream().findAny();
      return student;
    }

    @Override
    public Student save(Student student) {
      if (findAll().stream().map(Student::getId)
          .collect(Collectors.toList())
          .contains(student.getId())) {
        return update(student);
      }
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.save(student);
      transaction.commit();
      return student;
    }

    public Student update(Student student) {
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(student);
      transaction.commit();
      return student;
    }
}
