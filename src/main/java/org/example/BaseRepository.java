package org.example;

import java.util.List;

public interface BaseRepository<T, I> {
    boolean create(T element);
    T read(I id);
    int update(I id, T element);
    void delete(I id);

    List<T> findAll();
}
