package org.example.dao.impl;

import org.example.businesslogic.Util;
import org.example.dao.BaseRepository;
import org.example.entity.Cat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleCatRepository extends Util implements BaseRepository<Cat, Long> {

    private static String DB_DRIVER;
    private static String DB_URL;
    private static String tableName;
    private static Connection connection;

    public SimpleCatRepository() {
        DB_DRIVER = "org.h2.Driver";
        DB_URL = "jdbc:h2:mem:catsbase";
        tableName = "CATS";
        connection = getConnection(DB_DRIVER, DB_URL);
    }

    public static Connection getConnection() {
        return connection;
    }

    //create
    @Override
    public void createTable() throws SQLException {
        String sql = String.format(
                "CREATE TABLE %s (ID LONG, NAME VARCHAR(45), WEIGHT INT, ISANGRY BOOLEAN)", tableName);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void addElement(Cat cat) {
        String sql = String.format(
                "INSERT INTO %s (ID, NAME, WEIGHT, ISANGRY) VALUES (?, ?, ?, ?)", tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, cat.getId());
            preparedStatement.setString(2, cat.getName());
            preparedStatement.setInt(3, cat.getWeight());
            preparedStatement.setBoolean(4, cat.isAngry());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //read
    @Override
    public List<Cat> getAll() {
        List<Cat> cats = new ArrayList<>();
        String sql = String.format("SELECT ID, NAME, WEIGHT, ISANGRY FROM %s", tableName);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Cat cat = new Cat();
                cat.setId(resultSet.getLong("ID"));
                cat.setName(resultSet.getString("NAME"));
                cat.setWeight(resultSet.getInt("WEIGHT"));
                cat.setAngry(resultSet.getBoolean("ISANGRY"));
                cats.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }

    @Override
    public Cat getById(Long id) {
        String sql = String.format(
                "SELECT ID, NAME, WEIGHT, ISANGRY FROM %s WHERE ID=%d", tableName, id);
        Cat cat = new Cat();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cat.setId(resultSet.getLong("ID"));
                cat.setName(resultSet.getString("NAME"));
                cat.setWeight(resultSet.getInt("WEIGHT"));
                cat.setAngry(resultSet.getBoolean("ISANGRY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }

    //update
    @Override
    public void update(Long id, String newName, int newWeight, boolean isAngry) {
        String sql = String.format("UPDATE %s SET NAME=?, WEIGHT=?, ISANGRY=? WHERE ID=?", tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newWeight);
            preparedStatement.setBoolean(3, isAngry);
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    @Override
    public void remove(Long id) {
        String sql = String.format("DELETE FROM %s WHERE ID=?", tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}