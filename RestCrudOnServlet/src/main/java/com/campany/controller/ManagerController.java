package com.campany.controller;

import com.campany.dto.EmployeeDTO;
import com.campany.dto.ManagerDTO;
import com.campany.mapper.EmployeeMapper;
import com.campany.mapper.ManagerMapper;
import com.campany.repository.EmployeeRepository;
import com.campany.repository.ManagerRepository;
import com.campany.service.EmployeeService;
import com.campany.service.ManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebServlet("/RestCrudOnServlet/manager/*")
public class ManagerController extends HttpServlet {
    private ManagerService managerService;
    private EmployeeService employeeService;

    public void init() {
        managerService = new ManagerService(new ManagerRepository());
        employeeService = new EmployeeService(new EmployeeRepository());
    }

    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getById".equals(action)) {
            Integer managerId = Integer.parseInt(request.getParameter("id"));
            ManagerDTO managerDTO = managerService.getById(managerId);

            String managerJson = new ObjectMapper().writeValueAsString(managerDTO);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(managerJson);
        } else if ("getAll".equals(action)) {
            List<ManagerDTO> managerDTOList = managerService.getAll();

            // Преобразование списка employeeDTOs в JSON строку
            String managerJson = new ObjectMapper().writeValueAsString(managerDTOList);

            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Отправка JSON строки в ответе
            response.getWriter().write(managerJson);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)){
            // Логика создания нового сотрудника
            Integer managerId = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            BigDecimal salary = new BigDecimal(request.getParameter("salary"));
            String[] employeeIds = request.getParameterValues("employeeId");

            // Находим сотрудников по их id
            List<EmployeeDTO> employees = employeeService.findEmployeesByIds(Arrays.asList(employeeIds));

            managerService.create(new ManagerDTO(managerId, fullName, salary, employees));
        } else if ("update".equals(action)) {
            // Логика обновления информации о существующем сотруднике
            int managerId = Integer.parseInt(request.getParameter("id"));
            String newFullName = request.getParameter("newFullName");
            BigDecimal newSalary = new BigDecimal(request.getParameter("newSalary"));

            // Обновление информации о существующем сотруднике
            ManagerDTO managerDTO = new ManagerDTO(managerId, newFullName, newSalary);
            managerService.update(managerDTO);
        } else if ("delete".equals(action)) {
            // Логика удаления сотрудника
            int managerId = Integer.parseInt(request.getParameter("id"));
            managerService.deleteById(managerId);
        }
    }


}
