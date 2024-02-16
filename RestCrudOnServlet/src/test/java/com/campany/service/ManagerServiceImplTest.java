package com.campany.service;

import com.campany.dto.ManagerDTO;
import com.campany.entity.Manager;
import com.campany.repository.ManagerRepository;
import com.campany.service.impl.ManagerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceImplTest {
    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerServiceImpl managerServiceImpl;

    @Test
    public void testGetById() {
        // Создаем тестовые данные
        Integer id = 1;
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFullName("John Smith");

        // Устанавливаем ожидаемое поведение макета ManagerRepository
        when(managerRepository.findById(id)).thenReturn(manager);

        // Вызываем метод getById и проверяем результат
        ManagerDTO result = managerServiceImpl.getById(id);
        assertEquals(id, result.getId());
        assertEquals("John Smith", result.getFullName());

        // Проверяем, что метод findById был вызван с правильным аргументом
        verify(managerRepository).findById(id);
    }

    @Test
    public void testGetAll() {
        // Создаем тестовые данные
        List<Manager> managers = new ArrayList<>();
        Manager manager1 = new Manager();
        manager1.setId(1);
        manager1.setFullName("John Smith");
        managers.add(manager1);

        Manager manager2 = new Manager();
        manager2.setId(2);
        manager2.setFullName("Jane Doe");
        managers.add(manager2);

        // Устанавливаем ожидаемое поведение макета ManagerRepository
        when(managerRepository.findAll()).thenReturn(managers);

        // Вызываем метод getAll и проверяем результат
        List<ManagerDTO> result = managerServiceImpl.getAll();
        assertEquals(2, result.size());

        ManagerDTO managerDTO1 = result.get(0);
        assertEquals(manager1.getId(), managerDTO1.getId());
        assertEquals("John Smith", managerDTO1.getFullName());

        ManagerDTO managerDTO2 = result.get(1);
        assertEquals(manager2.getId(), managerDTO2.getId());
        assertEquals("Jane Doe", managerDTO2.getFullName());

        // Проверяем, что метод findAll был вызван
        verify(managerRepository).findAll();
    }

    @Test
    public void testCreate() {
        // Создание тестовых данных
        ManagerDTO managerDTO = new ManagerDTO(1,"John Doe", new BigDecimal("50000"));
        Manager manager = new Manager(1, "John Doe", new BigDecimal("50000"));

        // Stubbing метода save в репозитории для возвращения employee,
        // когда любой объект Employee передается в save
        doNothing().when(managerRepository).save(any(Manager.class));

        // Вызов тестируемого метода
        ManagerDTO result = managerServiceImpl.create(managerDTO);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(manager.getId(), result.getId());
        assertEquals(manager.getFullName(), result.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullEmployeeDTO() {
        // Вызов тестируемого метода с пустым объектом EmployeeDTO
        managerServiceImpl.create(null);
    }

    @Test
    public void testUpdateValidIdAndItem() {
        ManagerDTO item = new ManagerDTO();
        // Устанавливаем значения для объекта item
        item.setFullName("test");
        item.setSalary(new BigDecimal(500));

        doNothing().when(managerRepository).update(any(Manager.class));

        Integer id = 1;
        ManagerDTO updatedManager = managerServiceImpl.update(id, item);

        // Проверяем, что обновленный объект имеет ожидаемые значения
        assertEquals(updatedManager.getFullName(), item.getFullName());
        assertEquals(updatedManager.getSalary(), item.getSalary());

        verify(managerRepository, times(1)).update(any(Manager.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInvalidId() {
        Integer id = null;
        ManagerDTO item = new ManagerDTO();

        managerServiceImpl.update(id, item);

        // Добавим утверждение, чтобы убедиться, что исключение IllegalArgumentException было выброшено
        verify(managerRepository, never()).findById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInvalidItem() {
        Integer id = 1;
        ManagerDTO item = null;

        managerServiceImpl.update(id, item);

        // Добавим утверждение, чтобы убедиться, что исключение IllegalArgumentException было выброшено
        verify(managerRepository, never()).findById(any());
    }

    @Test
    public void testDeleteByIdValidId() {
        Integer id = 1;
        Manager manager = new Manager();

        // Подготовка моков для возвращаемых значений
        when(managerRepository.findById(id)).thenReturn(manager);

        // Вызов метода для тестирования
        managerServiceImpl.deleteById(id);

        // Утверждения
        verify(managerRepository, times(1)).findById(id);
        verify(managerRepository, times(1)).deleteById(id);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteByIdNotFound() {
        Integer id = 1;

        // Подготовка моков для возвращаемых значений
        when(managerRepository.findById(id)).thenReturn(null);

        // Вызов метода для тестирования
        managerServiceImpl.deleteById(id);

        // Утверждения
        verify(managerRepository, times(1)).findById(id);
        verify(managerRepository, never()).deleteById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteByIdInvalidId() {
        Integer id = null;

        // Вызов метода для тестирования
        managerServiceImpl.deleteById(id);

        // Утверждения
        verify(managerRepository, never()).findById(any());
        verify(managerRepository, never()).deleteById(any());
    }

}