package org.example.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.util.CustomUtil;
import org.example.dao.BaseRepository;
import org.example.entity.Cat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class SimpleCatRepository extends CustomUtil implements BaseRepository<Cat, Long> {

    private static String DB_DRIVER;
    private static String DB_URL;
    private static String tableName;
    private static Connection connection;

    public SimpleCatRepository() throws SQLException {
        DB_DRIVER = getPropertyValue("DB_DRIVER");
        DB_URL = getPropertyValue("DB_URL");
        tableName = getPropertyValue("TABLE_NAME");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setDriverClassName(DB_DRIVER);
        DataSource dataSource = new HikariDataSource(config);
        connection = dataSource.getConnection();
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    //create
    @Override
    public void createTable() throws SQLException {
        String sql = String.format(getPropertyValue("CREATE_TABLE"), tableName);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void addElement(Cat cat) {
        String sql = String.format(getPropertyValue("INSERT"), tableName);
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
        List<Cat> cats = null;
        String sql = String.format(getPropertyValue("SELECT_ALL"), tableName);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            cats = catListRowMapper.apply(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }

    @Override
    public Cat getById(Long id) {
        Cat cat = null;
        String sql = String.format(getPropertyValue("SELECT_BY_ID"), tableName, id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            cat = catListRowMapper.apply(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }

    //update
    @Override
    public void update(Long id, String newName, int newWeight, boolean isAngry) {
        String sql = String.format(getPropertyValue("UPDATE"), tableName);
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
        String sql = String.format(getPropertyValue("DELETE"), tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}