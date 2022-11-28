package org.example.util;

import org.example.entity.Cat;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class CustomUtil {

    public Function<ResultSet, List<Cat>> catListRowMapper = catList -> {
        List<Cat> cats = new ArrayList<>();
        try {
            while (catList.next()) {
                Cat cat = new Cat();
                cat.setId(catList.getLong("id"));
                cat.setName(catList.getString("name"));
                cat.setWeight(catList.getInt("weight"));
                cat.setAngry(catList.getBoolean("isAngry"));
                cats.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    };

    public static String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();
        try (InputStream inputStream = CustomUtil.class.getResourceAsStream("/app.properties")) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return propertyValue;
    }
}