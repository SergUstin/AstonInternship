package com.campany.service;

import java.util.List;

/**
 * Интерфейс для основных операций CRUD (создание, чтение, обновление, удаление) сущностей типа T.
 *
 * @param T сущности, с которым работает сервис
*/
public interface CrudService<T> {

    /**
     * Получает сущность по её идентификатору.
     *
     * @param id Идентификатор сущности
     * @return Объект сущности
     */
    T getById(Integer id);

    /**
     * Получает все сущности данного типа.
     *
     * @return Список всех сущностей
     */
    List<T> getAll();

    /**
     * Создаёт новую сущность.
     *
     * @param item Объект новой сущности
     * @return Созданная сущность
     * @throws ClassNotFoundException если класс не найден
     */
    T create(T item) throws ClassNotFoundException;

    /**
     * Обновляет сущность с указанным идентификатором.
     *
     * @param id Идентификатор сущности
     * @param item Объект сущности для обновления
     * @return Обновлённая сущность
     * @throws ClassNotFoundException если класс не найден
     */
    T update(Integer id, T item) throws ClassNotFoundException;

    /**
     * Удаляет сущность по её идентификатору.
     *
     * @param id Идентификатор сущности
     * @throws ClassNotFoundException если класс не найден
     */
    void deleteById(Integer id) throws ClassNotFoundException;
}
