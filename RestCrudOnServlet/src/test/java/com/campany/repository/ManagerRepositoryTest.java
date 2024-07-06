package com.campany.repository;

import com.campany.entity.Manager;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
public class ManagerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    private ManagerRepository managerRepository;
    private static TestConnectionToBase testConnectionToBase;

    @BeforeAll
    public static void setupDatabase() throws Exception {
        // Create the required table for testing
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            String createTable = "CREATE TABLE managers (id SERIAL PRIMARY KEY, full_name VARCHAR(255), salary DECIMAL)";
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
        managerRepository = new ManagerRepository(testConnectionToBase);
        clearDatabase(); // Clear the database before each test
    }

    private void clearDatabase() throws Exception {
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM managers")) {
            ps.executeUpdate();
        }
    }

    @Test
    public void testFindById_shouldReturnManager() throws Exception {
        // Arrange
        Integer id = 1;
        String fullName = "John Doe";
        BigDecimal salary = BigDecimal.valueOf(5000);

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, fullName);
            ps.setBigDecimal(3, salary);
            ps.executeUpdate();
        }

        // Act
        Manager result = managerRepository.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fullName, result.getFullName());
        assertEquals(salary, result.getSalary());
    }

    @Test
    public void testFindAll_shouldReturnListOfManagers() throws Exception {
        // Arrange
        Manager manager1 = new Manager(1, "John Doe", BigDecimal.valueOf(5000));
        Manager manager2 = new Manager(2, "Jane Smith", BigDecimal.valueOf(6000));

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)")) {
            ps.setInt(1, manager1.getId());
            ps.setString(2, manager1.getFullName());
            ps.setBigDecimal(3, manager1.getSalary());
            ps.executeUpdate();

            ps.setInt(1, manager2.getId());
            ps.setString(2, manager2.getFullName());
            ps.setBigDecimal(3, manager2.getSalary());
            ps.executeUpdate();
        }

        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertEquals(2, actualManagers.size());
        assertEquals(manager1.getFullName(), actualManagers.get(0).getFullName());
        assertEquals(manager1.getSalary(), actualManagers.get(0).getSalary());
        assertEquals(manager2.getFullName(), actualManagers.get(1).getFullName());
        assertEquals(manager2.getSalary(), actualManagers.get(1).getSalary());
    }

    @Test
    public void testFindAll_shouldReturnEmptyListWhenNoManagers() throws Exception {
        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertTrue(actualManagers.isEmpty());
    }

    @Test
    public void testSave_shouldSaveManagerSuccessfully() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        // Act
        managerRepository.save(manager);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM managers WHERE id = ?")) {
            ps.setInt(1, manager.getId());
            ResultSet resultSet = ps.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(manager.getId(), resultSet.getInt("id"));
            assertEquals(manager.getFullName(), resultSet.getString("full_name"));
            assertEquals(manager.getSalary(), resultSet.getBigDecimal("salary"));
        }
    }

    @Test
    public void testUpdate_shouldUpdateManagerSuccessfully() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)")) {
            ps.setInt(1, manager.getId());
            ps.setString(2, manager.getFullName());
            ps.setBigDecimal(3, manager.getSalary());
            ps.executeUpdate();
        }

        // Act
        manager.setSalary(BigDecimal.valueOf(6000));
        managerRepository.update(manager);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM managers WHERE id = ?")) {
            ps.setInt(1, manager.getId());
            ResultSet resultSet = ps.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(manager.getId(), resultSet.getInt("id"));
            assertEquals(manager.getFullName(), resultSet.getString("full_name"));
            assertEquals(manager.getSalary(), resultSet.getBigDecimal("salary"));
        }
    }

    @Test
    public void testDeleteById_shouldDeleteManagerSuccessfully() throws Exception {
        // Arrange
        int id = 1;
        Manager manager = new Manager(id, "John Doe", BigDecimal.valueOf(5000));

        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO managers (id, full_name, salary) VALUES (?, ?, ?)")) {
            ps.setInt(1, manager.getId());
            ps.setString(2, manager.getFullName());
            ps.setBigDecimal(3, manager.getSalary());
            ps.executeUpdate();
        }

        // Act
        managerRepository.deleteById(id);

        // Assert
        try (Connection connection = testConnectionToBase.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM managers WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            assertFalse(resultSet.next());
        }
    }
}
