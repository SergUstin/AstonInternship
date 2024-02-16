package com.campany.service.impl;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;
import com.campany.mapper.ManagerMapper;
import com.campany.repository.ManagerRepository;
import com.campany.service.CrudService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ManagerServiceImpl implements CrudService<ManagerDTO> {
    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public ManagerDTO getById(Integer id) {
        log.info("Запрос информации о менеджере с id: {}.", id);
        if (id != null) {
            Manager manager = managerRepository.findById(id);
            if (manager != null) {
                return ManagerMapper.toDTO(manager);
            } else {
                log.warn("Менеджер с id: {} не найден.", id);
                throw new RuntimeException("Сотрудник с id " + id + " не найден");
            }
        } else {
            log.error("Неверно указан id для запроса информации о менеджере.");
            throw new IllegalArgumentException("Неверно указан id для запроса информации о менеджере");
        }
    }

    @Override
    public List<ManagerDTO> getAll() {
        log.info("Запрос списка всех менеджеров.");
        List<Manager> managers = managerRepository.findAll();
        return ManagerMapper.toDTOList(managers);
    }

    @Override
    public ManagerDTO create(ManagerDTO item) {
        log.info("Создание нового менеджера.");
        if (item != null) {
            Manager manager = ManagerMapper.toEntity(item);
            managerRepository.save(manager);
            return ManagerMapper.toDTO(manager);
        } else {
            log.error("Невозможно создать менеджера: передан пустой объект ManagerDTO.");
            throw new IllegalArgumentException("Невозможно создать менеджера: передан пустой объект ManagerDTO");
        }
    }

    @Override
    public ManagerDTO update(Integer id, ManagerDTO item) {
        log.info("Обновление информации о менеджере с id: {}.", id);
        if (id != null && item != null) {
            item.setId(id);
            Manager manager = ManagerMapper.toEntity(item);
            managerRepository.update(manager);
            return ManagerMapper.toDTO(manager);
        } else {
            log.error("Невозможно обновить информацию о менеджере: указан неверный id или передан пустой объект ManagerDTO.");
            throw new IllegalArgumentException("Невозможно обновить информацию о менеджере: указан неверный id или передан пустой объект ManagerDTO.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Удаление менеджера с id: {}.", id);
        if (id != null) {
            Manager manager = managerRepository.findById(id);
            if (manager != null) {
                managerRepository.deleteById(id);
                log.info("Менеджер с id: {} удален успешно.", id);
            } else {
                log.warn("Менеджер с id: {} не найден, невозможно удалить.", id);
                throw new RuntimeException("Менеджер с id " + id + " не найден, невозможно удалить");
            }
        } else {
            log.error("Неверно указан id для удаления менеджера.");
            throw new IllegalArgumentException("Неверно указан id для удаления менеджера");
        }
    }
}
