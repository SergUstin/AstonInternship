package com.campany.controller;

import com.campany.dto.ManagerDTO;
import com.campany.service.impl.ManagerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ManagerControllerTest {
    @Mock
    private ManagerServiceImpl managerServiceImpl;

    @InjectMocks
    private ManagerController managerController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoGet_withActionGetById_shouldReturnEmployeeById() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(1);
        managerDTO.setFullName("John Smith");
        managerDTO.setSalary(new BigDecimal(50000));

        when(request.getParameter("action")).thenReturn("getById");
        when(request.getParameter("id")).thenReturn("1");
        when(managerServiceImpl.getById(1)).thenReturn(managerDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        managerController.doGet(request, response);
        writer.flush();

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals("{\"id\":1,\"fullName\":\"John Smith\",\"salary\":50000}", stringWriter.toString());
    }

    @Test
    public void testDoGet_withActionGetAll_shouldReturnAllEmployees() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ManagerDTO manager1 = new ManagerDTO();
        manager1.setId(1);
        manager1.setFullName("John Smith");
        manager1.setSalary(new BigDecimal(50000));

        ManagerDTO manager2 = new ManagerDTO();
        manager2.setId(2);
        manager2.setFullName("Jane Doe");
        manager2.setSalary(new BigDecimal(60000));

        List<ManagerDTO> managerDTOS = Arrays.asList(manager1, manager2);

        when(request.getParameter("action")).thenReturn("getAll");
        when(managerServiceImpl.getAll()).thenReturn(managerDTOS);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        managerController.doGet(request, response);
        writer.flush();

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals("[{\"id\":1,\"fullName\":\"John Smith\",\"salary\":50000},{\"id\":2,\"fullName\":\"Jane Doe\",\"salary\":60000}]", stringWriter.toString());
    }

    @Test
    public void testDoPost_withActionCreate_shouldCreateEmployeeAndReturnJson() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Устанавливаем параметры запроса
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("fullName")).thenReturn("John Doe");
        when(request.getParameter("salary")).thenReturn("5000");
        when(request.getParameter("managerId")).thenReturn("1");

        // Создаем employeeDTO, который ожидается вернуться при создании
        ManagerDTO createdManagerDTO = new ManagerDTO(1, "John Doe", new BigDecimal(5000));
        // Задаем поведение сервиса при вызове create
        when(managerServiceImpl.create(any(ManagerDTO.class))).thenReturn(createdManagerDTO);

        // Создаем StringWriter, чтобы записать результаты в него
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        // Act
        managerController.doPost(request, response);

        // Assert
        verify(managerServiceImpl).create(any(ManagerDTO.class));

        // Проверяем, что корректный JSON был записан в response
        String expectedJson = "{\"id\":1,\"fullName\":\"John Doe\",\"salary\":5000}";
        assertEquals(expectedJson, stringWriter.toString().trim());
    }

    @Test
    public void testDoPut() throws ServletException, IOException {
        // Создание HttpServletRequest и HttpServletResponse мок-объектов
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Задание параметров запроса
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("fullName")).thenReturn("John Doe");
        when(request.getParameter("salary")).thenReturn("1000");

        // Создание PrintWriter мок-объекта
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        // Связывание мок-объекта с HttpServletResponse
        when(response.getWriter()).thenReturn(writer);

        // Вызов метода контроллера
        managerController.doPut(request, response);

        // Проверка вызовов методов
        verify(managerServiceImpl).update(1, new ManagerDTO("John Doe", new BigDecimal("1000")));
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(Mockito.anyString());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        // Создание HttpServletRequest и HttpServletResponse мок-объектов
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Задание параметров запроса
        Mockito.when(request.getParameter("action")).thenReturn("delete");
        Mockito.when(request.getParameter("id")).thenReturn("1");

        // Вызов метода контроллера
        managerController.doDelete(request, response);

        // Проверка вызова метода
        Mockito.verify(managerServiceImpl).deleteById(1);
    }
}