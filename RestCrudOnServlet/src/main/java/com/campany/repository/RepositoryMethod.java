package com.campany.repository;

import java.util.List;

public interface RepositoryMethod<T> {
    T findById(Integer id);
    List<T> findAll();
    void save(T item) throws ClassNotFoundException;
    void update(T item) throws ClassNotFoundException;
    void deleteById(Integer id) throws ClassNotFoundException;
}
