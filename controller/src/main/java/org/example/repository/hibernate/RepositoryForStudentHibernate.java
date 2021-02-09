package org.example.repository.hibernate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.model.AbstractPerson;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    List<Student> students = findAll();
    students.removeIf(Objects::isNull);
    Student student = null;
    for (Student s : students) {
      if (s.getLogin().equals(login) && s.getPassword().equals(password)) {
        student = s;
      }
    }
    return Optional.ofNullable(student);
  }

  @Override
  public List<Student> findAll() {
    return (List<Student>) HibernateSessionFactory.getSessionFactory().openSession()
        .createQuery("from Student ").list();
  }

  @Override
  public Optional<Student> findById(int id) {
    Student student = HibernateSessionFactory.getSessionFactory().openSession()
        .get(Student.class, id);
    return Optional.ofNullable(student);
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
