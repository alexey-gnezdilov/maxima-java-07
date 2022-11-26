package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<Cat, Long> {

    //create
    void createTable() throws SQLException;

    void addElement(Cat cat) throws SQLException;

    //read
    List<Cat> getAll();

    Cat getById(Long id);

    //update
    void update(java.lang.Long id, String newName, int newWeight, boolean isAngry) throws SQLException;

    //delete
    void remove(Long id);
}
