package com.tubes.picturesharing;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    T save(T entity);
    Optional<T> findById(int id);
    List<T> findAll();
    int update(T entity);
    boolean deleteById(int id);
}