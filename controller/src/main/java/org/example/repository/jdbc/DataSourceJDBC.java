package org.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceJDBC {

  private static final String URL = "jdbc:postgresql://localhost:5432/pasha";
  private static final String USER = "pasha";
  private static final String PASSWORD = "qwe";
  private static final String DRIVER_NAME = "org.postgresql.Driver";

  public static Connection getConnection() throws SQLException {
    try {
      Class.forName(DRIVER_NAME);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
