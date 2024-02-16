package com.campany.controller;

import com.campany.dto.ManagerDTO;
import com.campany.repository.ConnectionToBase;
import com.campany.repository.ManagerRepository;
import com.campany.service.impl.ManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/manager/*")
public class ManagerController extends HttpServlet {
    private ManagerServiceImpl managerServiceImpl;
    public void init() {
        managerServiceImpl = new ManagerServiceImpl(new ManagerRepository(new ConnectionToBase()));
    }
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getById".equals(action)) {
            Integer managerId = Integer.parseInt(request.getParameter("id"));
            ManagerDTO managerDTO = managerServiceImpl.getById(managerId);
            String managerJson = new ObjectMapper().writeValueAsString(managerDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(managerJson);
        } else if ("getAll".equals(action)) {
            List<ManagerDTO> managerDTOList = managerServiceImpl.getAll();
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
            ManagerDTO managerDTO = managerServiceImpl.create(new ManagerDTO(managerId, fullName, salary));
            // Преобразование списка employeeDTOs в JSON строку
            String employeeJson = new ObjectMapper().writeValueAsString(managerDTO);
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
            int managerId = Integer.parseInt(request.getParameter("id"));
            String newFullName = request.getParameter("fullName");
            BigDecimal newSalary = new BigDecimal(request.getParameter("salary"));
            // Обновление информации о существующем сотруднике
            ManagerDTO managerDTO = managerServiceImpl.update(managerId, new ManagerDTO(newFullName, newSalary));
            // Преобразование списка employeeDTOs в JSON строку
            String managerJson = new ObjectMapper().writeValueAsString(managerDTO);
            // Установка типа содержимого и кодировки для ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // Отправка JSON строки в ответе
            response.getWriter().write(managerJson);

        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            // Логика удаления сотрудника
            int managerId = Integer.parseInt(request.getParameter("id"));
            managerServiceImpl.deleteById(managerId);
        }
    }
}
