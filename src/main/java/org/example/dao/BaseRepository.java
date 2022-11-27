package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T, I> {

    //create
    void createTable() throws SQLException;

    void addElement(T element);

    //read
    List<T> getAll();

    T getById(I id);

    //update
    void update(I id, String newName, int newWeight, boolean isAngry);

    //delete
    void remove(I id);
}
