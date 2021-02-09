package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.model.AbstractPerson;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    List<Teacher> teachers = findAll();
    Teacher teacher = null;
    for (Teacher t: teachers) {
      if (t.getLogin().equals(login) && t.getPassword().equals(password)) {
        teacher = t;
      }
    }
    return Optional.ofNullable(teacher);
  }

  @Override
  public List<Teacher> findAll() {
    return (List<Teacher>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Teacher ").list();

  }

  @Override
  public Optional<Teacher> findById(int id) {
    Teacher teacher = HibernateSessionFactory.getSessionFactory().openSession().get(Teacher.class, id);
    return Optional.ofNullable(teacher);
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
