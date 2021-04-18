package org.example.repository.hibernate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.group.Group;
import org.example.repository.interfaces.RepositoryForGroupInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository("hibernateG")
public class RepositoryForGroupHibernate implements RepositoryForGroupInterface {

  @Override
  public List<Group> findAll() {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    List<Group> from_group = (List<Group>) session
        .createQuery("from Group ").list();
    //session.close();
    return from_group;
  }

  @Override
  public Optional<Group> findById(int id) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Query<Group> query = (Query<Group>) session
        .createQuery("from Group where id = :id");
    query.setParameter("id", id);
    Optional<Group> any = query.stream().findAny();
    //session.close();
    return any;
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
    //session.close();
    return group;
  }

  public Group update(Group group) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.merge(group);
    transaction.commit();
    //session.close();
    return group;
  }

  @Override
  public Group remove(Group group) {
    return null;
  }
}
