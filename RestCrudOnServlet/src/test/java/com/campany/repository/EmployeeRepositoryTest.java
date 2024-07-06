package com.campany.repository;

import com.campany.entity.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class EmployeeRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    private EmployeeRepository employeeRepository;
    private static TestConnectionToBase testConnectionToBase;

    @BeforeAll
    public static void setupDatabase() throws Exception {
        // Create the required table for testing
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            String createTable = "CREATE TABLE employees (id SERIAL PRIMARY KEY, full_name VARCHAR(255), salary DECIMAL, manager_id INT)";
            connection.createStatement().executeUpdate(createTable);
        }

        // Initialize testConnectionToBase
        testConnectionToBase = new TestConnectionToBase(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
    }

    @BeforeEach
    public void setup() throws Exception {
        employeeRepository = new EmployeeRepository(testConnectionToBase);
        clearDatabase(); // Clear the database before each test
    }

    private void clearDatabase() throws Exception {
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM employees")) {
            ps.executeUpdate();
        }
    }

    @Test
    public void testGetById_ShouldFindEmployeeById_WhenEmployeeDBIsNotEmpty() throws Exception {
        // Arrange
        Integer id = 1;
        String fullName = "John Doe";
        BigDecimal salary = new BigDecimal("5000.00");
        Integer managerId = 2;

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, fullName);
            ps.setBigDecimal(3, salary);
            ps.setInt(4, managerId);
            ps.executeUpdate();
        }

        // Act
        Employee result = employeeRepository.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fullName, result.getFullName());
        assertEquals(salary, result.getSalary());
        assertEquals(managerId, result.getManagerId());
    }

    @Test
    public void testFindById_shouldReturnNullIfEmployeeNotExists() throws Exception {
        // Act
        Employee result = employeeRepository.findById(999); // Non-existing ID

        // Assert
        assertNull(result);
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

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (full_name, salary, manager_id) VALUES (?, ?, ?)")) {
            ps.setString(1, fullName1);
            ps.setBigDecimal(2, salary1);
            ps.setInt(3, managerId1);
            ps.executeUpdate();

            ps.setString(1, fullName2);
            ps.setBigDecimal(2, salary2);
            ps.setInt(3, managerId2);
            ps.executeUpdate();
        }

        // Act
        List<Employee> actualEmployees = employeeRepository.findAll();

        // Assert
        assertEquals(2, actualEmployees.size());
        assertEquals(fullName1, actualEmployees.get(0).getFullName());
        assertEquals(salary1, actualEmployees.get(0).getSalary());
        assertEquals(managerId1, actualEmployees.get(0).getManagerId());

        assertEquals(fullName2, actualEmployees.get(1).getFullName());
        assertEquals(salary2, actualEmployees.get(1).getSalary());
        assertEquals(managerId2, actualEmployees.get(1).getManagerId());
    }

    @Test
    public void testSave_shouldSaveEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee(1, "John Doe", new BigDecimal("5000.00"), 2);

        // Act
        employeeRepository.save(employee);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            ps.setInt(1, employee.getId());
            ResultSet resultSet = ps.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(employee.getId(), resultSet.getInt("id"));
            assertEquals(employee.getFullName(), resultSet.getString("full_name"));
            assertEquals(employee.getSalary(), resultSet.getBigDecimal("salary"));
            assertEquals(employee.getManagerId(), resultSet.getInt("manager_id"));
        }
    }

    @Test
    public void testUpdate_shouldUpdateEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee(1, "John Doe", new BigDecimal("5000.00"), 2);

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getFullName());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setInt(4, employee.getManagerId());
            ps.executeUpdate();
        }

        // Act
        employee.setSalary(new BigDecimal("6000.00"));
        employeeRepository.update(employee);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            ps.setInt(1, employee.getId());
            ResultSet resultSet = ps.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(employee.getId(), resultSet.getInt("id"));
            assertEquals(employee.getFullName(), resultSet.getString("full_name"));
            assertEquals(employee.getSalary(), resultSet.getBigDecimal("salary"));
            assertEquals(employee.getManagerId(), resultSet.getInt("manager_id"));
        }
    }

    @Test
    public void testDeleteById_shouldDeleteEmployee() throws Exception {
        // Arrange
        Integer id = 1;
        Employee employee = new Employee(id, "John Doe", new BigDecimal("5000.00"), 2);

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (id, full_name, salary, manager_id) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getFullName());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setInt(4, employee.getManagerId());
            ps.executeUpdate();
        }

        // Act
        employeeRepository.deleteById(id);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            assertFalse(resultSet.next());
        }
    }
}


