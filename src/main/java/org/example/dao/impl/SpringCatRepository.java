package org.example.dao.impl;

import org.example.dao.CatRepository;
import org.example.entity.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringCatRepository implements CatRepository {

    @Autowired private DataSource dataSource;
    @Autowired private RowMapper<Cat> catRowMapper;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE cats (id INT, Name VARCHAR(45), Weight INT, isAngry BIT)");
        addElement(new Cat(1L, "Murzik", 10, true));
        addElement(new Cat(2L, "Barsik", 2, false));
        addElement(new Cat(3L, "Persik", 5, true));
        addElement(new Cat(4L, "Persik", 7, false));
    }

    @Override
    public void createTable() {}

    @Override
    public void addElement(Cat cat) {
        jdbcTemplate.update("INSERT INTO cats (id, Name, Weight, isAngry) VALUES (?, ?, ?, ?)",
                cat.getId(),
                cat.getName(),
                cat.getWeight(),
                cat.isAngry());
    }

    @Override
    public Cat getById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM cats WHERE id = ?", new BeanPropertyRowMapper<>(Cat.class), id).get(0);
    }

    @Override
    public List<Cat> getAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cats", catRowMapper));
    }

    @Override
    public void update(Long id, String newName, int newWeight, boolean isAngry) {
        jdbcTemplate.update("UPDATE cats SET NAME = ?, WEIGHT = ?, ISANGRY = ? WHERE ID = ?",
                newName,
                newWeight,
                isAngry,
                id);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM cats WHERE ID = ?", id);
    }
}