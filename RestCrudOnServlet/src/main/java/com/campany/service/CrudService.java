package com.campany.service;

import java.util.List;

public interface CrudService<T> {
    T getById(Integer id);
    List<T> getAll();
    void create(T item);
    void update(T item);
    void deleteById(Integer id);
}
