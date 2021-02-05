package org.example.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    List<Student> students = new ArrayList<>();
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection
            .prepareStatement("select * from student where id = ?;")) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        students.add(new Student(rs.getInt("id"),
            rs.getString("login"), rs.getString("password"),
            rs.getString("name"), rs.getInt("age")));
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    setRatingForStudentWithId(students, id);
    return students.stream().findAny();
  }

  @Override
  public Student save(Student student) {
    Optional<Student> studentOptional = findById(student.getId());
    if (studentOptional.isPresent()) {
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
      preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try (Connection connection = DataSource.getConnection()) {
      for (Map.Entry<Subject, Integer> entry : student.getRatings().entrySet()) {
        PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into rating (student_id, subject, rating) values (?, ?, ?);");
        preparedStatement.setInt(1, student.getId());
        preparedStatement.setString(2, Subject.getStringBySubject(entry.getKey()));
        preparedStatement.setInt(3, entry.getValue());
        preparedStatement.executeQuery();
        preparedStatement.close();
      }
    } catch (SQLException e) {
        e.printStackTrace();
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
      preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try (Connection connection = DataSource.getConnection()) {
      for (Map.Entry<Subject, Integer> entry : student.getRatings().entrySet()) {
        PreparedStatement preparedStatement = connection.prepareStatement("update rating " +
            "set rating = ? where student_id = ? and subject = ?;");
        preparedStatement.setInt(1, entry.getValue());
        preparedStatement.setInt(2, student.getId());
        preparedStatement.setString(3, Subject.getStringBySubject(entry.getKey()));
        preparedStatement.executeQuery();
        preparedStatement.close();
      }
    } catch (SQLException e) {
        e.printStackTrace();
      }
    return student;
  }

  @Override
  public Student remove(Student student) {
    return null;
  }

  @Override
  public Optional<Student> findByLoginAndPassword(String login, String password) {
    List<Student> students = new ArrayList<>();
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection
            .prepareStatement("select * from student where login = ? and password = ?;")) {
      preparedStatement.setString(1, login);
      preparedStatement.setString(2, password);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        students.add(new Student(rs.getInt("id"),
            rs.getString("login"), rs.getString("password"),
            rs.getString("name"), rs.getInt("age")));
      }

    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    Optional<Student> s = students.stream().findAny();
    if (s.isPresent()) {
      int id = s.get().getId();
      setRatingForStudentWithId(students, id);
    }
    return students.stream().findAny();
  }

  private void setRatingForStudentWithId(List<Student> students, int id) {
    try (Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection
            .prepareStatement("select * from rating where student_id = ?;")
    ) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      //инициализируем рейтинг у студентов, если он есть в таблице
      while (rs.next()) {
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
  }
}
