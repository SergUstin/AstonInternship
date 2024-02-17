package com.campany.controller;

import com.campany.dto.EmployeeDTO;
import com.campany.service.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {
    @Mock
    private EmployeeServiceImpl employeeServiceImpl;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoGet_withActionGetById_shouldReturnEmployeeById() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class); // есть mockMvc а тут ты пишешь тесты ради тестов, все замокано
        HttpServletResponse response = mock(HttpServletResponse.class);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setFullName("John Smith");
        employeeDTO.setSalary(new BigDecimal(50000));
        employeeDTO.setManagerId(50);

        when(request.getParameter("action")).thenReturn("getById");
        when(request.getParameter("id")).thenReturn("1");
        when(employeeServiceImpl.getById(1)).thenReturn(employeeDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeController.doGet(request, response);
        writer.flush();

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals("{\"id\":1,\"fullName\":\"John Smith\",\"salary\":50000,\"managerId\":50}", stringWriter.toString());
    }

    @Test
    public void testDoGet_withActionGetAll_shouldReturnAllEmployees() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(1);
        employee1.setFullName("John Smith");
        employee1.setSalary(new BigDecimal(50000));
        employee1.setManagerId(50);

        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setId(2);
        employee2.setFullName("Jane Doe");
        employee2.setSalary(new BigDecimal(60000));
        employee2.setManagerId(2);

        List<EmployeeDTO> employeeDTOS = Arrays.asList(employee1, employee2);

        when(request.getParameter("action")).thenReturn("getAll");
        when(employeeServiceImpl.getAll()).thenReturn(employeeDTOS);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeController.doGet(request, response);
        writer.flush();

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        assertEquals("[{\"id\":1,\"fullName\":\"John Smith\",\"salary\":50000,\"managerId\":50},{\"id\":2,\"fullName\":\"Jane Doe\",\"salary\":60000,\"managerId\":2}]", stringWriter.toString());
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
        EmployeeDTO createdEmployeeDTO = new EmployeeDTO(1, "John Doe", new BigDecimal(5000), 1);
        // Задаем поведение сервиса при вызове create
        when(employeeServiceImpl.create(any(EmployeeDTO.class))).thenReturn(createdEmployeeDTO);

        // Создаем StringWriter, чтобы записать результаты в него
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        // Act
        employeeController.doPost(request, response);

        // Assert
        verify(employeeServiceImpl).create(any(EmployeeDTO.class));

        // Проверяем, что корректный JSON был записан в response
        String expectedJson = "{\"id\":1,\"fullName\":\"John Doe\",\"salary\":5000,\"managerId\":1}";
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
        when(request.getParameter("managerId")).thenReturn("2");

        // Создание PrintWriter мок-объекта
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        // Связывание мок-объекта с HttpServletResponse
        when(response.getWriter()).thenReturn(writer);

        // Вызов метода контроллера
        employeeController.doPut(request, response);

        // Проверка вызовов методов
        verify(employeeServiceImpl).update(1, new EmployeeDTO("John Doe", new BigDecimal("1000"), 2));
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
        employeeController.doDelete(request, response);

        // Проверка вызова метода
        Mockito.verify(employeeServiceImpl).deleteById(1);
    }



}