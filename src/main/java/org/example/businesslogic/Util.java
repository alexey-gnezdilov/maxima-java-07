package org.example.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public Connection getConnection(String DB_DRIVER, String DB_URL) {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error!");
        }
        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
        System.out.println("Connection closed!");
        connection.close();
    }
}