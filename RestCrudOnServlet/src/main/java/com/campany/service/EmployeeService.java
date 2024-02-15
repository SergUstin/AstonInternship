package com.campany.service;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;
import com.campany.mapper.EmployeeMapper;
import com.campany.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EmployeeService implements CrudService<EmployeeDTO> {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO getById(Integer id) {
        log.info("Запрос информации о сотруднике с id: {}.", id);
        if (id != null) {
            Employee employee = employeeRepository.findById(id);
            if (employee != null) {
                return EmployeeMapper.toDTO(employee);
            } else {
                log.warn("Сотрудник с id: {} не найден.", id);
                throw new RuntimeException("Сотрудник с id " + id + " не найден");
            }
        } else {
            log.error("Неверно указан id для запроса информации о сотруднике.");
            throw new IllegalArgumentException("Неверно указан id для запроса информации о сотруднике");
        }
    }

    @Override
    public List<EmployeeDTO> getAll() {
        log.info("Запрос списка всех сотрудников.");
        List<Employee> employees = employeeRepository.findAll();
        return EmployeeMapper.toDTOList(employees);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO item) {
        log.info("Создание нового сотрудника.");
        if (item != null) {
            Employee employee = EmployeeMapper.toEntity(item);
            employeeRepository.save(employee);
            return EmployeeMapper.toDTO(employee);
        } else {
            log.error("Невозможно создать сотрудника: передан пустой объект EmployeeDTO.");
            throw new IllegalArgumentException("Невозможно создать сотрудника: передан пустой объект EmployeeDTO");
        }
    }

    @Override
    public EmployeeDTO update(Integer id, EmployeeDTO item) {
        log.info("Обновление информации о сотруднике с id: {}.", id);
        if (id != null && item != null) {
            item.setId(id);
            Employee employee = EmployeeMapper.toEntity(item);
            employeeRepository.update(employee);
            return EmployeeMapper.toDTO(employee);
        } else {
            log.error("Невозможно обновить информацию о сотруднике: указан неверный id или передан пустой объект EmployeeDTO.");
            throw new IllegalArgumentException("Невозможно обновить информацию о сотруднике: указан неверный id или передан пустой объект EmployeeDTO.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Удаление сотрудника с id: {}.", id);
        if (id != null) {
            Employee employee = employeeRepository.findById(id);
            if (employee != null) {
                employeeRepository.deleteById(id);
                log.info("Сотрудник с id: {} удален успешно.", id);
            } else {
                log.warn("Сотрудник с id: {} не найден, невозможно удалить.", id);
                throw new RuntimeException("Сотрудник с id " + id + " не найден, невозможно удалить");
            }
        } else {
            log.error("Неверно указан id для удаления сотрудника.");
            throw new IllegalArgumentException("Неверно указан id для удаления сотрудника");
        }
    }
}
