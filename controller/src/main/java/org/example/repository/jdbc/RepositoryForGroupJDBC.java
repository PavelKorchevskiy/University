package org.example.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForGroupInterface;
//import org.example.repository.producer.StudentProducer;
//import org.example.repository.producer.TeacherProducer;
import org.example.subject.Subject;

public class RepositoryForGroupJDBC implements RepositoryForGroupInterface {

  private static volatile RepositoryForGroupJDBC instance;

  private RepositoryForGroupJDBC() {
  }

  public static RepositoryForGroupJDBC getInstance() {
    if (instance == null) {
      synchronized (RepositoryForGroupJDBC.class) {
        if (instance == null) {
          instance = new RepositoryForGroupJDBC();
        }
      }
    }
    return instance;
  }

  @Override
  public List<Group> findAll() {
    List<Group> groups = new ArrayList<>();
    //создаем группы без студентов
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from groups;");
        ResultSet rs = ps.executeQuery()
    ) {
      while (rs.next()) {
        int id = rs.getInt("id");
//        Teacher teacher = TeacherProducer.getRepository()
//            .findById(rs.getInt("teacher_id")).orElseThrow(NullPointerException::new);
//        Set<Subject> subjects = getSubjectsFromDB(id);
//        Set<Student> students = new HashSet<>();
//        groups.add(new Group(id, teacher, students, subjects));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //добавляем студентов в группы
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from group_student;");
        ResultSet rs = ps.executeQuery()
    ) {
      while (rs.next()) {
        int groupId = rs.getInt("group_id");
        int studentId = rs.getInt("student_id");
//        Student student = StudentProducer.getRepository().findById(studentId)
//            .orElseThrow(NullPointerException::new);
//        for (Group g : groups) {
//          if (g.getId() == groupId) {
//            g.getStudents().add(student);
//          }
//        }
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return groups;
  }

  @Override
  public Optional<Group> findById(int id) {
    List<Group> groups = new ArrayList<>();
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from groups where id = ?;")) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

//      while (rs.next()) {
//        Teacher teacher = TeacherProducer.getRepository()
//            .findById(rs.getInt("teacher_id")).orElseThrow(NullPointerException::new);
//        Set<Subject> subjects = getSubjectsFromDB(id);
//        Set<Student> students = new HashSet<>();
//        groups.add(new Group(id, teacher, students, subjects));
//      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection
            .prepareStatement("select * from group_student where group_id = ?;")
    ) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//        int studentId = rs.getInt("student_id");
//        Student student = StudentProducer.getRepository().findById(studentId)
//            .orElseThrow(NullPointerException::new);
//        for (Group g : groups) {
//          g.getStudents().add(student);
//        }
//      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return groups.stream().findAny();
  }

  @Override
  public Group save(Group group) {
    return null;
  }

  @Override
  public Group remove(Group group) {
    return null;
  }

  private Set<Subject> getSubjectsFromDB(int groupId) {
    Set<Subject> subjects = new HashSet<>();
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection
            .prepareStatement("select from groups_subject where group_id = ?");
    ) {
      ps.setInt(1, groupId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        subjects.add(Subject.getSubjectByString(rs.getString("subject")));
      }
      rs.close();
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return subjects;
  }
}
