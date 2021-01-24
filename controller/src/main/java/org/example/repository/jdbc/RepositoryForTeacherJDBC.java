package org.example.repository.jdbc;

import org.example.model.Teacher;
import org.example.repository.interfaces.RepositoryForTeachersInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositoryForTeacherJDBC implements RepositoryForTeachersInterface {

    private static volatile RepositoryForTeacherJDBC instance;

    private RepositoryForTeacherJDBC() {}

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
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from teacher;");
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                List<BigDecimal> salary;
                String salaryStr = rs.getString("salary");
                salary = Arrays.stream(salaryStr.split(";")).map(s -> BigDecimal.valueOf(Double.parseDouble(s))).collect(Collectors.toList());
                teachers.add(new Teacher(rs.getInt("id"),
                        rs.getString("login"), rs.getString("password"),
                        rs.getString("name"), rs.getInt("age"), salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    @Override
    public Optional<Teacher> findById(int id) {
        List<Teacher> teachers = new ArrayList<>();
        try(Connection connection = DataSource.getConnection()
            ) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from teacher where id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                List<BigDecimal> salary;
                String salaryStr = rs.getString("salary");
                salary = Arrays.stream(salaryStr.split(";")).map(s -> BigDecimal.valueOf(Double.parseDouble(s))).collect(Collectors.toList());
                teachers.add(new Teacher(rs.getInt("id"),
                        rs.getString("login"), rs.getString("password"),
                        rs.getString("name"), rs.getInt("age"), salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers.stream().findAny();
    }

    @Override
    public Teacher save(Teacher teacher) {
        List<Teacher> teachers = findAll();
        if (teachers.stream().map(Teacher::getId).collect(Collectors.toList()).contains(teacher.getId())) {
            return update(teacher);
        }
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into teacher" +
                    " (id, login, password, name, age, salary) values (?, ?, ?, ?, ?, ?);")
        ) {

            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, teacher.getLogin());
            preparedStatement.setString(3, teacher.getPassword());
            preparedStatement.setString(4, teacher.getName());
            preparedStatement.setInt(5, teacher.getAge());
            preparedStatement.setString(6, getSalaryAsString(teacher.getSalary()));
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    public Teacher update(Teacher teacher) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update teacher set " +
                    "login = ?, password = ?, name = ?, age = ?, salary = ? where id = ?")
        ) {

            preparedStatement.setInt(6, teacher.getId());
            preparedStatement.setString(1, teacher.getLogin());
            preparedStatement.setString(2, teacher.getPassword());
            preparedStatement.setString(3, teacher.getName());
            preparedStatement.setInt(4, teacher.getAge());
            preparedStatement.setString(5, getSalaryAsString(teacher.getSalary()));
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            rs.close();
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
        /*List<Teacher> teachers = new ArrayList<>();
        try(Connection connection = DataSource.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from teacher where login = ? and password = ?;");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                List<BigDecimal> salary;
                String salaryStr = rs.getString("salary");
                salary = Arrays.stream(salaryStr.split(";"))
                        .map(s -> BigDecimal.valueOf(Double.parseDouble(s)))
                        .collect(Collectors.toList());
                teachers.add(new Teacher(rs.getInt("id"),
                        rs.getString("login"), rs.getString("password"),
                        rs.getString("name"), rs.getInt("age"), salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers.stream().findAny();*/
        Optional<Teacher> result = Optional.ofNullable(null);
        List<Teacher> teachers = findAll();
        for (Teacher t: teachers) {
            if (t.getLogin().equals(login) && t.getPassword().equals(password)) {
                result = Optional.ofNullable(t);
            }
        }
        return result;
    }

    private String getSalaryAsString(List<BigDecimal> list) {
        StringBuilder sb = new StringBuilder();
        for (BigDecimal bd: list) {
            sb.append(bd.setScale(2, RoundingMode.HALF_UP)).append(";");
        }
        sb.deleteCharAt(sb.lastIndexOf(";"));
        return sb.toString();
    }
}
