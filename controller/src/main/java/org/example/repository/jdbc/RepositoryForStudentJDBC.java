package org.example.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.model.Student;
import org.example.repository.interfaces.RepositoryForStudentsInterface;
import org.example.subject.Subject;

public class RepositoryForStudentJDBC implements RepositoryForStudentsInterface {

  private static volatile RepositoryForStudentJDBC instance;

  private RepositoryForStudentJDBC() {
  }

  public static RepositoryForStudentJDBC getInstance() {
    if (instance == null) {
      synchronized (RepositoryForStudentJDBC.class) {
        if (instance == null) {
          instance = new RepositoryForStudentJDBC();
        }
      }
    }
    return instance;
  }

  @Override
  public List<Student> findAll() {
    List<Student> students = new ArrayList<>();
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from student;");
        ResultSet rs = preparedStatement.executeQuery()) {
      while (rs.next()) {
        students.add(new Student(rs.getInt("id"),
            rs.getString("login"), rs.getString("password"),
            rs.getString("name"), rs.getInt("age")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from rating;");
        ResultSet rs = preparedStatement.executeQuery()) {
      //инициализируем рейтинг у студентов, если он есть в таблице
      while (rs.next()) {
        int id = rs.getInt("student_id");
        Subject subject = Subject.getSubjectByString(rs.getString("subject"));
        int rating = rs.getInt("rating");
        for (Student student : students) {
          if (student.getId() == id) {
            student.putRating(subject, rating);
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return students;
  }

  @Override
  public Optional<Student> findById(int id) {
    List<Student> students = findAll();
    Optional<Student> result = Optional.empty();
    for (Student s : students) {
      if (s.getId() == id) {
        result = Optional.of(s);
      }
    }
    return result;
  }

  @Override
  public Student save(Student student) {
    List<Student> students = findAll();
    if (students.stream().map(Student::getId).collect(Collectors.toList())
        .contains(student.getId())) {
      return update(student);
    }
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into student (id, login, password, name, age) values (?, ?, ?, ?, ?);")
    ) {
      preparedStatement.setInt(1, student.getId());
      preparedStatement.setString(2, student.getLogin());
      preparedStatement.setString(3, student.getPassword());
      preparedStatement.setString(4, student.getName());
      preparedStatement.setInt(5, student.getAge());
      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //set rating
    for (Map.Entry<Subject, Integer> entry : student.getRatings().entrySet()) {
      try (Connection connection = DataSource.getConnection();
          PreparedStatement ps2 = connection.prepareStatement(
              "insert into rating (student_id, subject, rating) values (?, ?, ?);")
      ) {
        ps2.setInt(1, entry.getValue());
        ps2.setInt(2, student.getId());
        ps2.setString(3, Subject.getStringBySubject(entry.getKey()));
        ResultSet rs1 = ps2.executeQuery();
        rs1.next();
        rs1.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return student;
  }

  public Student update(Student student) {
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update student" +
            " set  login = ?, password = ?, name = ?, age = ? where id = ?;")
    ) {
      preparedStatement.setInt(5, student.getId());
      preparedStatement.setString(1, student.getLogin());
      preparedStatement.setString(2, student.getPassword());
      preparedStatement.setString(3, student.getName());
      preparedStatement.setInt(4, student.getAge());
      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //set rating
    for (Map.Entry<Subject, Integer> entry : student.getRatings().entrySet()) {
      try (Connection connection = DataSource.getConnection();
          PreparedStatement ps2 = connection.prepareStatement("update rating " +
              "set rating = ? where student_id = ? and subject = ?;")
      ) {
        ps2.setInt(1, entry.getValue());
        ps2.setInt(2, student.getId());
        ps2.setString(3, Subject.getStringBySubject(entry.getKey()));
        ResultSet rs1 = ps2.executeQuery();
        rs1.next();
        rs1.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return student;
  }

  @Override
  public Student remove(Student student) {
    return null;
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    List<Student> students = findAll();
    Optional<Student> result = Optional.empty();
    for (Student s : students) {
      if (s.getLogin().equals(login) && s.getPassword().equals(password)) {
        result = Optional.of(s);
      }
    }
    return result;
  }
}
