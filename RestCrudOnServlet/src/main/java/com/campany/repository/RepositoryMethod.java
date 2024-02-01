package com.campany.repository;

import java.util.List;

public interface RepositoryMethod<T> {
    T findById(Integer id);
    List<T> findAll();
    void create(T item);
    void update(T item);
    void deleteById(Integer id);
}
