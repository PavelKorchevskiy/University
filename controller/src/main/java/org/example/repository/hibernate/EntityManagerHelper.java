package org.example.repository.hibernate;

import javax.persistence.EntityManager;
import lombok.SneakyThrows;
import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerHelper {

  private final SessionFactory factory;

  @SneakyThrows
  private EntityManagerHelper() {
    Configuration configuration = new Configuration().configure();
    configuration.addAnnotatedClass(Student.class);
    configuration.addAnnotatedClass(Teacher.class);
    configuration.addAnnotatedClass(Group.class);
    this.factory = configuration.buildSessionFactory();
  }

  private static class EntityManagerHelperHolder {

    private static final EntityManagerHelper HOLDER_INSTANCE = new EntityManagerHelper();
  }

  public static EntityManagerHelper getInstance() {
    return EntityManagerHelper.EntityManagerHelperHolder.HOLDER_INSTANCE;
  }

  public EntityManager getEntityManager() {
    return factory.createEntityManager();
  }
}

