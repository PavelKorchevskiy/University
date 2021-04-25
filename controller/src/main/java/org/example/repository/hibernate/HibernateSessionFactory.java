package org.example.repository.hibernate;

import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

  private static SessionFactory sessionFactory;

  private HibernateSessionFactory() {
  }

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      synchronized (HibernateSessionFactory.class) {
        if (sessionFactory == null) {
          Configuration configuration = new Configuration().configure();
          configuration.addAnnotatedClass(Student.class);
          configuration.addAnnotatedClass(Teacher.class);
          configuration.addAnnotatedClass(Group.class);
          StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
              .applySettings(configuration.getProperties());
          sessionFactory = configuration.buildSessionFactory(builder.build());
        }
      }
    }
    return sessionFactory;
  }
}
