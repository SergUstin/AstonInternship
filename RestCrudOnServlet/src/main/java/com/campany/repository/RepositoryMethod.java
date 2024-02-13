package com.campany.repository;

import java.util.List;

/**
 * Интерфейс для основных операций хранения и управления сущностями типа T в репозитории.
 *
 * @param <T> Тип сущности, с которым работает репозиторий
 */
public interface RepositoryMethod<T> {
    /**
     * Находит сущность по её идентификатору.
     *
     * @param id Идентификатор сущности
     * @return Объект сущности
     */
    T findById(Integer id);

    /**
     * Возвращает список всех сущностей данного типа.
     *
     * @return Список всех сущностей
     */
    List<T> findAll();

    /**
     * Сохраняет новую сущность.
     *
     * @param item Объект новой сущности
     * @throws ClassNotFoundException если класс не найден
     */
    void save(T item) throws ClassNotFoundException;

    /**
     * Обновляет существующую сущность.
     *
     * @param item Объект сущности для обновления
     * @throws ClassNotFoundException если класс не найден
     */
    void update(T item) throws ClassNotFoundException;

    /**
     * Удаляет сущность по её идентификатору.
     *
     * @param id Идентификатор сущности
     * @throws ClassNotFoundException если класс не найден
     */
    void deleteById(Integer id) throws ClassNotFoundException;
}
