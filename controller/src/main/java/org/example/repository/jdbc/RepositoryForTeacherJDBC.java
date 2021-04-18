package org.example.repository.jdbc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;

public class RepositoryForTeacherJDBC implements RepositoryForTeachersInterface {

  private static volatile RepositoryForTeacherJDBC instance;

  private RepositoryForTeacherJDBC() {
  }

  public static RepositoryForTeacherJDBC getInstance() {
    if (instance == null) {
      synchronized (RepositoryForTeacherJDBC.class) {
        if (instance == null) {
          instance = new RepositoryForTeacherJDBC();
        }
      }
    }
    return instance;
  }

  @Override
  public List<Teacher> findAll() {
    List<Teacher> teachers = new ArrayList<>();
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from teacher;");
        ResultSet rs = preparedStatement.executeQuery()) {
      getTeachersFromResultSet(teachers, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return teachers;
  }

  @Override
  public Optional<Teacher> findById(int id) {
    List<Teacher> teachers = new ArrayList<>();
    try (Connection connection = DataSourceJDBC.getConnection()
    ) {
      PreparedStatement preparedStatement = connection
          .prepareStatement("select * from teacher where id = ?;");
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      getTeachersFromResultSet(teachers, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return teachers.stream().findAny();
  }

  private void getTeachersFromResultSet(List<Teacher> teachers, ResultSet rs) throws SQLException {
    while (rs.next()) {
      int id = rs.getInt("id");
      List<BigDecimal> salary = getSalaryById(id);
      teachers.add(new Teacher(id, rs.getString("login"), rs.getString("password")
          , rs.getString("name"), rs.getInt("age"), salary));
    }
  }

  @Override
  public Teacher save(Teacher teacher) {
    Optional<Teacher> optionalTeacher = findById(teacher.getId());
    if (optionalTeacher.isPresent()) {
      return update(teacher);
    }
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into teacher (id, login, password, name, age, salary) values (?, ?, ?, ?, ?, ?);")
    ) {
      preparedStatement.setInt(1, teacher.getId());
      preparedStatement.setString(2, teacher.getLogin());
      preparedStatement.setString(3, teacher.getPassword());
      preparedStatement.setString(4, teacher.getName());
      preparedStatement.setInt(5, teacher.getAge());
      preparedStatement.setString(6, getSalaryAsString(teacher.getSalary()));
      preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return teacher;
  }

  public Teacher update(Teacher teacher) {
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update teacher set " +
            "login = ?, password = ?, name = ?, age = ?, salary = ? where id = ?")
    ) {
      preparedStatement.setInt(6, teacher.getId());
      preparedStatement.setString(1, teacher.getLogin());
      preparedStatement.setString(2, teacher.getPassword());
      preparedStatement.setString(3, teacher.getName());
      preparedStatement.setInt(4, teacher.getAge());
      preparedStatement.setString(5, getSalaryAsString(teacher.getSalary()));
      preparedStatement.executeQuery();
      updateSalary(teacher.getId(), teacher.getSalary());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return teacher;
  }

  @Override
  public Teacher remove(Teacher teacher) {
    return null;
  }

  @Override
  public Optional<Teacher> findByLoginAndPassword(String login, String password) {
    List<Teacher> teachers = new ArrayList<>();
    try (Connection connection = DataSourceJDBC.getConnection()
    ) {
      PreparedStatement preparedStatement = connection
          .prepareStatement("select * from teacher where login = ? and password = ?;");
      preparedStatement.setString(1, login);
      preparedStatement.setString(2, password);
      ResultSet rs = preparedStatement.executeQuery();
      getTeachersFromResultSet(teachers, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return teachers.stream().findAny();
  }

  private String getSalaryAsString(List<BigDecimal> list) {
    StringBuilder sb = new StringBuilder();
    for (BigDecimal bd : list) {
      sb.append(bd.setScale(2, RoundingMode.HALF_UP)).append(";");
    }
    sb.deleteCharAt(sb.lastIndexOf(";"));
    return sb.toString();
  }

  private List<BigDecimal> getSalaryById(int id) {
    List<BigDecimal> salary = new ArrayList<>();
    try (Connection connection = DataSourceJDBC.getConnection()) {
      PreparedStatement ps = connection
          .prepareStatement("select * from salary where teacher_id = ?;");
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        salary.add(rs.getBigDecimal("salary"));
      }
      rs.close();
      ps.close();
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return salary;
  }

  private void updateSalary(int teacherId, List<BigDecimal> salary) {
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection
            .prepareStatement("delete from salary where teacher_id = ?;")) {
      ps.setInt(1, teacherId);
      ps.executeQuery();
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    try (Connection connection = DataSourceJDBC.getConnection();
        PreparedStatement ps = connection
            .prepareStatement("insert into salary (teacher_id, salary) values (?, ?);;")) {
      for (BigDecimal bd : salary) {
        ps.setInt(1, teacherId);
        ps.setBigDecimal(2, bd);
        ps.executeQuery();
      }

    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }
}
