package com.campany.controller;

import com.campany.dto.EmployeeDTO;
import com.campany.dto.ManagerDTO;
import com.campany.entity.Employee;
import com.campany.entity.Manager;
import com.campany.mapper.EmployeeMapper;
import com.campany.mapper.ManagerMapper;
import com.campany.repository.EmployeeRepository;
import com.campany.repository.ManagerRepository;
import com.campany.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class EmployeeController extends HttpServlet {
    private EmployeeService employeeService;

    public void init() {
        employeeService = new EmployeeService(new EmployeeRepository());
    }

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
        if ("create".equals(action)){
            // Логика создания нового сотрудника
            String fullName = request.getParameter("fullName");
            BigDecimal salary = new BigDecimal(request.getParameter("salary"));
            int managerId = Integer.parseInt(request.getParameter("managerId"));
            ManagerRepository managerRepository = new ManagerRepository();
            ManagerDTO managerDTO = ManagerMapper.toDTO(managerRepository.findById(managerId));

            employeeService.create(new EmployeeDTO(fullName, salary, managerDTO));
        } else if ("update".equals(action)) {

            // Логика обновления информации о существующем сотруднике
            int employeeId = Integer.parseInt(request.getParameter("id"));
            String newFullName = request.getParameter("newFullName");
            BigDecimal newSalary = new BigDecimal(request.getParameter("newSalary"));

            // Обновление информации о существующем сотруднике, включая менеджера
            EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, newFullName, newSalary);
            employeeService.update(employeeDTO);
        } else if ("delete".equals(action)) {

            // Логика удаления сотрудника
            int employeeId = Integer.parseInt(request.getParameter("id"));
            employeeService.deleteById(employeeId);
        }
    }


}
