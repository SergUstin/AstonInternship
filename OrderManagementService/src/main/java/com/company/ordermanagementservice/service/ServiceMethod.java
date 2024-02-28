package com.company.ordermanagementservice.service;

import java.math.BigInteger;
import java.util.List;

public interface ServiceMethod<T> {
    T getById(BigInteger id);
    List<T> getAll();
    T create(T item);
    T update(BigInteger id, T item);
    void deleteById(BigInteger id);
}
