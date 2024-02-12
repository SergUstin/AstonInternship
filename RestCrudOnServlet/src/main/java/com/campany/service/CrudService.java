package com.campany.service;

import java.util.List;

public interface CrudService<T> {
    T getById(Integer id);
    List<T> getAll();
    T create(T item) throws ClassNotFoundException;
    T update(Integer id, T item) throws ClassNotFoundException;
    void deleteById(Integer id) throws ClassNotFoundException;
}
