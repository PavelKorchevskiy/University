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
import org.example.repository.producer.StudentProducer;
import org.example.repository.producer.TeacherProducer;
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
    try (Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("select  * from groups;");
        ResultSet rs = ps.executeQuery()
    ) {
      while (rs.next()) {
        int id = rs.getInt("id");
        Teacher teacher = TeacherProducer.getRepository()
            .findById(rs.getInt("teacher_id")).orElseThrow(NullPointerException::new);
        Set<Subject> subjects = getSubjectsFromString(rs.getString("subjects"));
        Set<Student> students = new HashSet<>();
        groups.add(new Group(id, teacher, students, subjects));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //добавляем студентов в группы
    try (Connection connection = DataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("select  * from group_student;");
        ResultSet rs = ps.executeQuery();
    ) {
      while (rs.next()) {
        int groupId = rs.getInt("group_id");
        int studentId = rs.getInt("student_id");
        Student student = StudentProducer.getRepository().findById(studentId)
            .orElseThrow(NullPointerException::new);
        for (Group g : groups) {
          if (g.getId() == groupId) {
            g.getStudents().add(student);
          }
        }
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return groups;
  }

  @Override
  public Optional<Group> findById(int id) {
    List<Group> groups = findAll();
    Group group = null;
    for (Group g : groups) {
      if (g.getId() == id) {
        group = g;
      }
    }
    return Optional.ofNullable(group);
  }

  @Override
  public Group save(Group group) {
    return null;
  }

  @Override
  public Group remove(Group group) {
    return null;
  }

  private Set<Subject> getSubjectsFromString(String string) {
    Set<Subject> subjects = new HashSet<>();
    String[] array = string.split(";");
    for (String s : array) {
      subjects.add(Subject.getSubjectByString(s));
    }
    return subjects;
  }
}
