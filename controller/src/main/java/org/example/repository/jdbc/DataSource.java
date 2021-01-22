package org.example.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static volatile DataSource instance;
    private static final String url = "jdbc:postgresql://localhost:5432/pasha";
    private static final String user = "pasha";
    private static final String password = "qwe";
    private static final String driverName = "org.postgresql.Driver";

//    private DataSource() {
//    }
//
//    public static DataSource getInstance() {
//        if (instance == null) {
//            synchronized (DataSource.class) {
//                if (instance == null) {
//                    instance = new DataSource();
//                }
//            }
//        }
//        return instance;
//    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }
}
