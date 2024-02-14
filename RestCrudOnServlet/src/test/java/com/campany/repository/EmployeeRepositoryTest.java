package com.campany.repository;

import com.campany.entity.Employee;
import com.campany.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryTest {

    @Mock
    private ConnectionToBase connection;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement statementMock;

    @Mock
    private ResultSet resultSetMock;

    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetById_ShouldFindEmployeeById_WhenEmployeeBDIsNotEmpty() throws SQLException, ClassNotFoundException {
        // Arrange
        Integer id = 1;
        String fullName = "John Doe";
        BigDecimal salary = new BigDecimal("5000.00");
        Integer managerId = 2;

        when(connection.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt("id")).thenReturn(id);
        when(resultSetMock.getString("full_name")).thenReturn(fullName);
        when(resultSetMock.getBigDecimal("salary")).thenReturn(salary);
        when(resultSetMock.getInt("manager_id")).thenReturn(managerId);

        // Act
        Employee result = employeeRepository.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fullName, result.getFullName());
        assertEquals(salary, result.getSalary());
        assertEquals(managerId, result.getManagerId());

        verify(connection).getConnection();
        verify(connectionMock).prepareStatement(anyString());
        verify(statementMock).setInt(1, id);
        verify(statementMock).executeQuery();
        verify(resultSetMock).next();
        verify(resultSetMock).getInt("id");
        verify(resultSetMock).getString("full_name");
        verify(resultSetMock).getBigDecimal("salary");
        verify(resultSetMock).getInt("manager_id");
    }

    @Test
    public void testFindById_shouldReturnNullIfEmployeeNotExists() throws Exception {
        // Arrange
        Integer id = 1;

        when(connection.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        // Act
        Employee result = employeeRepository.findById(id);

        // Assert
        assertNull(result);

        verify(connection).getConnection();
        verify(connectionMock).prepareStatement(anyString());
        verify(statementMock).setInt(1, id);
        verify(statementMock).executeQuery();
        verify(resultSetMock).next();
    }

    @Test
    public void testFindAll_shouldReturnAllEmployees() throws Exception {
        // Arrange
        String fullName1 = "John Doe";
        BigDecimal salary1 = new BigDecimal("5000.00");
        Integer managerId1 = 2;

        String fullName2 = "Jane Smith";
        BigDecimal salary2 = new BigDecimal("6000.00");
        Integer managerId2 = 3;

        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(1, fullName1, salary1, managerId1));
        expectedEmployees.add(new Employee(2, fullName2, salary2, managerId2));

        when(connection.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, true, false);
        when(resultSetMock.getInt("id")).thenReturn(1, 2);
        when(resultSetMock.getString("full_name")).thenReturn(fullName1, fullName2);
        when(resultSetMock.getBigDecimal("salary")).thenReturn(salary1, salary2);
        when(resultSetMock.getInt("manager_id")).thenReturn(managerId1, managerId2);

        // Act
        List<Employee> actualEmployees = employeeRepository.findAll();

        // Assert
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertEquals(expectedEmployees.get(0), actualEmployees.get(0));
        assertEquals(expectedEmployees.get(1), actualEmployees.get(1));
    }

    @Test
    public void testSave_shouldSaveEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee(1, "John Doe", new BigDecimal("5000.00"), 2);

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        employeeRepository.save(employee);

        // Assert
        // Verify that getConnection method is called once
        verify(connection, times(1)).getConnection();

        // Verify that the prepareStatement method is called once and the correct SQL query is passed as argument
        verify(connectionMock, times(1))
                .prepareStatement("INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)");

        // Verify that the setInt, setString, setBigDecimal methods of the PreparedStatement are called with the correct arguments
        verify(statementMock, times(1)).setInt(1, 1);
        verify(statementMock, times(1)).setString(2, "John Doe");
        verify(statementMock, times(1)).setBigDecimal(3, new BigDecimal("5000.00"));
        verify(statementMock, times(1)).setInt(4, 2);

        // Verify that the executeUpdate method is called once
        verify(statementMock, times(1)).executeUpdate();
    }

    @Test
    public void testUpdate_shouldUpdateEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee(1, "John Doe", new BigDecimal("6000.00"), 2);

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        employeeRepository.update(employee);

        // Assert
        verify(connection).getConnection();
        verify(connectionMock).prepareStatement("UPDATE employees SET full_name = ?, salary = ?, manager_id = ? WHERE id = ?");
        verify(statementMock).setString(1, "John Doe");
        verify(statementMock).setBigDecimal(2, new BigDecimal("6000.00"));
        verify(statementMock).setInt(3, 2);
        verify(statementMock).setInt(4, 1);
        verify(statementMock).executeUpdate();
    }

    @Test
    public void testDeleteById_shouldDeleteEmployee() throws Exception {
        // Arrange
        Integer id = 1;

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        employeeRepository.deleteById(id);

        // Assert
        verify(connection).getConnection();
        verify(connectionMock).prepareStatement("DELETE FROM employees WHERE id = ?");
        verify(statementMock).setInt(1, id);
        verify(statementMock).executeUpdate();
    }
}