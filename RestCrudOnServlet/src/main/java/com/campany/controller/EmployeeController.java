package com.campany.controller;

import com.campany.dto.EmployeeDTO;
import com.campany.repository.EmployeeRepository;
import com.campany.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/employee/*")
public class EmployeeController extends HttpServlet {
    private EmployeeService employeeService;
    public void init() {
        employeeService = new EmployeeService(new EmployeeRepository());    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getById".equals(action)) {
            Integer employeeId = Integer.parseInt(request.getParameter("id"));
            EmployeeDTO employeeDTO = employeeService.getById(employeeId);
            // Преобразование объекта employeeDTO в JSON строку
            String employeeJson = new ObjectMapper().writeValueAsString(employeeDTO);
            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Отправка JSON строки в ответ
            response.getWriter().write(employeeJson);
        } else if ("getAll".equals(action)) {
            List<EmployeeDTO> employeeDTOS = employeeService.getAll();
            // Преобразование списка employeeDTOs в JSON строку
            String employeeJson = new ObjectMapper().writeValueAsString(employeeDTOS);
            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Отправка JSON строки в ответе
            response.getWriter().write(employeeJson);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            // Логика создания нового сотрудника
            int employeeId = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            BigDecimal salary = new BigDecimal(request.getParameter("salary"));
            Integer managerId = Integer.parseInt(request.getParameter("managerId"));
            EmployeeDTO employeeDTO = employeeService.create(new EmployeeDTO(employeeId, fullName, salary, managerId));
            // Преобразование списка employeeDTOs в JSON строку
            String employeeJson = new ObjectMapper().writeValueAsString(employeeDTO);
            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Отправка JSON строки в ответе
            response.getWriter().write(employeeJson);
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            // Логика обновления информации о существующем сотруднике
            int employeeId = Integer.parseInt(request.getParameter("id"));
            String newFullName = request.getParameter("fullName");
            BigDecimal newSalary = new BigDecimal(request.getParameter("salary"));
            Integer newManagerId = Integer.parseInt(request.getParameter("managerId"));
            // Обновление информации о существующем сотруднике
            EmployeeDTO employeeDTO = new EmployeeDTO(newFullName, newSalary, newManagerId);
            EmployeeDTO employee = employeeService.update(employeeId, employeeDTO);
            // Преобразование списка employeeDTOs в JSON строку
            String employeeJson = new ObjectMapper().writeValueAsString(employee);
            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Отправка JSON строки в ответе
            response.getWriter().write(employeeJson);
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            // Логика удаления сотрудника
            int employeeId = Integer.parseInt(request.getParameter("id"));
            employeeService.deleteById(employeeId);
        }
    }
}
