package com.campany.service;

import com.campany.dto.EmployeeDTO;
import com.campany.entity.Employee;
import com.campany.repository.EmployeeRepository;
import com.campany.service.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {
    private EmployeeRepository employeeRepository;
    private EmployeeServiceImpl employeeServiceImpl;

    @Before
    public void setup() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeServiceImpl = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void testGetEmployeeById() {
        // Создаем ожидаемый объект DTO с заполненными данными
        EmployeeDTO expectedDto = new EmployeeDTO(1, "John Doe",new BigDecimal("50000"), 5);

        // Настроим репозиторий для возвращения ожидаемых данных
        when(employeeRepository.findById(1)).thenReturn(new Employee(1, "John Doe", new BigDecimal("50000"), 5));

        // Выполним метод
        EmployeeDTO actualDto = employeeServiceImpl.getById(1);

        // Проверим, что метод вернул ожидаемый объект
        assertEquals(expectedDto, actualDto);

        // Проверим, что метод репозитория был вызван 1 раз с нужным параметром
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testGetEmployeeByIdWithNullId() {
        employeeServiceImpl.getById(null); // Вызовем метод с null в качестве id, ожидаем RuntimeException
    }

    @Test
    public void testGetAll() {
        // Создание тестовых данных
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", new BigDecimal("50000"), 1));
        employees.add(new Employee(2, "Jane Smith", new BigDecimal("60000"), 2));
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        // Вызов тестируемого метода
        List<EmployeeDTO> result = employeeServiceImpl.getAll();

        // Проверка результатов
        assertEquals(employees.size(), result.size());
        assertEquals(employees.get(0).getFullName(), result.get(0).getFullName());
        assertEquals(employees.get(1).getFullName(), result.get(1).getFullName());
    }

    @Test
    public void testCreateNewEmployee() {
        // Создание тестовых данных
        EmployeeDTO employeeDTO = new EmployeeDTO(1,"John Doe", new BigDecimal("50000"), 1);
        Employee employee = new Employee(1, "John Doe", new BigDecimal("50000"), 1);

        // Stubbing метода save в репозитории для возвращения employee,
        // когда любой объект Employee передается в save
        doNothing().when(employeeRepository).save(any(Employee.class));

        // Вызов тестируемого метода
        EmployeeDTO result = employeeServiceImpl.create(employeeDTO);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFullName(), result.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullEmployeeDTO() {
        // Вызов тестируемого метода с пустым объектом EmployeeDTO
        employeeServiceImpl.create(null);
    }

    @Test
    public void testUpdateValidIdAndItem() {
        EmployeeDTO item = new EmployeeDTO();
        // Устанавливаем значения для объекта item
        item.setFullName("test");
        item.setSalary(new BigDecimal(500));
        item.setManagerId(200);

        doNothing().when(employeeRepository).update(any(Employee.class));

        Integer id = 1;
        EmployeeDTO updatedEmployee = employeeServiceImpl.update(id, item);

        // Проверяем, что обновленный объект имеет ожидаемые значения
        assertEquals(updatedEmployee.getFullName(), item.getFullName());
        assertEquals(updatedEmployee.getSalary(), item.getSalary());
        assertEquals(updatedEmployee.getManagerId(), item.getManagerId());

        verify(employeeRepository, times(1)).update(any(Employee.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInvalidId() {
        Integer id = null;
        EmployeeDTO item = new EmployeeDTO();

        employeeServiceImpl.update(id, item);

        // Добавим утверждение, чтобы убедиться, что исключение IllegalArgumentException было выброшено
        verify(employeeRepository, never()).findById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInvalidItem() {
        Integer id = 1;
        EmployeeDTO item = null;

        employeeServiceImpl.update(id, item);

        // Добавим утверждение, чтобы убедиться, что исключение IllegalArgumentException было выброшено
        verify(employeeRepository, never()).findById(any());
    }

    @Test
    public void testDeleteByIdValidId() {
        Integer id = 1;
        Employee employee = new Employee();

        // Подготовка моков для возвращаемых значений
        when(employeeRepository.findById(id)).thenReturn(employee);

        // Вызов метода для тестирования
        employeeServiceImpl.deleteById(id);

        // Утверждения
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeRepository, times(1)).deleteById(id);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteByIdNotFound() {
        Integer id = 1;

        // Подготовка моков для возвращаемых значений
        when(employeeRepository.findById(id)).thenReturn(null);

        // Вызов метода для тестирования
        employeeServiceImpl.deleteById(id);

        // Утверждения
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteByIdInvalidId() {
        Integer id = null;

        // Вызов метода для тестирования
        employeeServiceImpl.deleteById(id);

        // Утверждения
        verify(employeeRepository, never()).findById(any());
        verify(employeeRepository, never()).deleteById(any());
    }

}