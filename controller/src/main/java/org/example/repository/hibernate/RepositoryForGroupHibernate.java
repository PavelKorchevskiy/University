package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.group.Group;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RepositoryForGroupHibernate implements RepositoryForGroupInterface {

  private static volatile RepositoryForGroupHibernate instance;

  private RepositoryForGroupHibernate() {
  }

  public static RepositoryForGroupHibernate getInstance() {
    if (instance == null) {
      synchronized (RepositoryForGroupHibernate.class) {
        if (instance == null) {
          instance = new RepositoryForGroupHibernate();
        }
      }
    }
    return instance;
  }

  @Override
  public List<Group> findAll() {
    return (List<Group>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Group ").list();
  }

  @Override
  public Optional<Group> findById(int id) {
    Group group = HibernateSessionFactory.getSessionFactory().openSession().get(Group.class, id);
    return Optional.ofNullable(group);
  }

  @Override
  public Group save(Group group) {
    if (findAll().stream().map(Group::getId).collect(Collectors.toList()).contains(group.getId())) {
      return update(group);
    }
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.save(group);
    transaction.commit();
    session.close();
    return group;
  }

  public Group update(Group group) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.merge(group);
    transaction.commit();
    session.close();
    return group;
  }

  @Override
  public Group remove(Group group) {
    return null;
  }
}
