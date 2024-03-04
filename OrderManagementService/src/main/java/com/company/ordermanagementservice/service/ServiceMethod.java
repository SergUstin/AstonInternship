package com.company.ordermanagementservice.service;

import java.math.BigInteger;
import java.util.List;

/**
 * Общий интерфейс, представляющий методы для выполнения операций сущности.
 * @param <T> тип данных
 */
public interface ServiceMethod<T> {
    /**
     * Получает объект по идентификатору.
     * @param id идентификатор объекта
     * @return объект типа T
     */
    T getById(BigInteger id);

    /**
     * Получает все объекты.
     * @return список объектов типа T
     */
    List<T> getAll();

    /**
     * Создает новый объект.
     * @param item объект, который необходимо создать
     * @return созданный объект типа T
     */
    T create(T item);

    /**
     * Обновляет объект по идентификатору.
     * @param id идентификатор объекта
     * @param item объект, который необходимо обновить
     * @return обновленный объект типа T
     */
    T update(BigInteger id, T item);

    /**
     * Удаляет объект по идентификатору.
     * @param id идентификатор объекта, который необходимо удалить
     */
    void deleteById(BigInteger id);
}
