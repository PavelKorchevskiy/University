package org.example.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import org.example.group.Group;
import org.example.model.Student;
import org.example.repository.hibernate.RepositoryForGroupHibernate;
import org.example.repository.jdbc.DataSource;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitRating {

  //класс для стартового инициализирования таблицы rating нулями
  private final Logger log = LoggerFactory.getLogger(InitRating.class);

  private void logging() {
    log.error("Start log");
  }

  public static void main(String[] args) {
    new InitRating().logging();




//    for (Group group : RepositoryForGroupHibernate.getInstance().findAll()) {
//      Set<Student> students = group.getStudents();
//      Set<Subject> subjects = group.getSubjects();
//      for (Student student : students) {
//        for (Subject subject : subjects) {
//          try (Connection connection = DataSource.getConnection();
//              PreparedStatement ps = connection.prepareStatement(
//                  "insert into aaarating (student_id, subject, rating) values (?, ?, ?)")
//          ) {
//            ps.setInt(1, student.getId());
//            ps.setString(2, subject.toString());
//            ps.setInt(3, 0);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//          } catch (SQLException e) {
//            e.printStackTrace();
//          }
//        }
//      }
//    }
  }
}
