package org.example.businesslogic;

import org.example.entity.Cat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Util {

    public Function<ResultSet, Cat> catRowMapper = cat -> {
        try {
            return new Cat(
                    cat.getLong("id"),
                    cat.getString("name"),
                    cat.getInt("weight"),
                    cat.getBoolean("isAngry")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public Function<ResultSet, List<Cat>> catListRowMapper = catList -> {
        try {
            List<Cat> cats = new ArrayList<>();
            while (catList.next()) {
                Cat cat = catRowMapper.apply(catList);
                cats.add(cat);
            }
            return cats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

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