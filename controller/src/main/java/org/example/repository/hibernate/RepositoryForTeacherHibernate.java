package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import org.example.model.AbstractPerson;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Restrictions;
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
    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Teacher.class).add(Restrictions.eq("login", login))
        .add(Restrictions.eq("password", password));
    List<Teacher> teachers = criteria.list();
    return teachers.stream().findAny();

//    Query query = HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Teacher where Teacher.login = :login and Teacher.password = :password");
//    query.setParameter("login", login);
//    query.setParameter("password", password);
//    return query.list().stream().findFirst();



//    List<Teacher> teachers = findAll();
//    Teacher teacher = null;
//    for (Teacher t: teachers) {
//      if (t.getLogin().equals(login) && t.getPassword().equals(password)) {
//        teacher = t;
//      }
//    }
//    return Optional.ofNullable(teacher);
  }

  @Override
  public List<Teacher> findAll() {
    return (List<Teacher>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Teacher ").list();

  }

  @Override
  public Optional<Teacher> findById(int id) {
    Session session  = HibernateSessionFactory.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Teacher.class).add(Restrictions.eq("id", id));
    List<Teacher> teachers = criteria.list();
    return teachers.stream().findAny();

//    Query query = HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Teacher where Teacher.id = :id");
//    query.setParameter("id", id);
//    return query.stream().findAny();
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
