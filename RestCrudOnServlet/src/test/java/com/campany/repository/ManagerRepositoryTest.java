package com.campany.repository;

import com.campany.entity.Manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManagerRepositoryTest {

    @Mock
    private ConnectionToBase connection;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement statementMock;

    @Mock
    private ResultSet resultSetMock;

    @InjectMocks
    private ManagerRepository managerRepository;

    @Test
    public void testFindById_shouldReturnManager() throws Exception {
        // Arrange
        Integer id = 1;
        String fullName = "John Doe";
        BigDecimal salary = BigDecimal.valueOf(5000);

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeQuery methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mocking the next method of resultSet
        when(resultSetMock.next()).thenReturn(true);

        // Mocking the getInt, getString, and getBigDecimal methods of resultSet
        when(resultSetMock.getInt("id")).thenReturn(id);
        when(resultSetMock.getString("full_name")).thenReturn(fullName);
        when(resultSetMock.getBigDecimal("salary")).thenReturn(salary);

        // Act
        Manager result = managerRepository.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fullName, result.getFullName());
        assertEquals(salary, result.getSalary());

        // Verify the method calls
        verify(connection).getConnection();
        verify(connectionMock).prepareStatement(anyString());
        verify(statementMock).setInt(1, id);
        verify(statementMock).executeQuery();
        verify(resultSetMock).next();
        verify(resultSetMock).getInt("id");
        verify(resultSetMock).getString("full_name");
        verify(resultSetMock).getBigDecimal("salary");
    }

    @Test
    public void testFindAll_shouldReturnListOfManagers() throws Exception {
        // Arrange
        List<Manager> expectedManagers = new ArrayList<>();
        expectedManagers.add(new Manager(1, "John Doe", BigDecimal.valueOf(5000)));
        expectedManagers.add(new Manager(2, "Jane Smith", BigDecimal.valueOf(6000)));

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeQuery methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mocking the next method of resultSet
        when(resultSetMock.next()).thenReturn(true, true, false);

        // Mocking the getInt, getString, and getBigDecimal methods of resultSet
        when(resultSetMock.getInt("id")).thenReturn(1, 2);
        when(resultSetMock.getString("full_name")).thenReturn("John Doe", "Jane Smith");
        when(resultSetMock.getBigDecimal("salary")).thenReturn(BigDecimal.valueOf(5000), BigDecimal.valueOf(6000));

        // Create the ManagerRepository instance
        ManagerRepository managerRepository = new ManagerRepository(connection);

        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertEquals(expectedManagers.get(0).getId(), actualManagers.get(0).getId());
        assertEquals(expectedManagers.get(0).getFullName(), actualManagers.get(0).getFullName());
        assertEquals(expectedManagers.get(0).getSalary(), actualManagers.get(0).getSalary());
        assertEquals(expectedManagers.get(1).getId(), actualManagers.get(1).getId());
        assertEquals(expectedManagers.get(1).getFullName(), actualManagers.get(1).getFullName());
        assertEquals(expectedManagers.get(1).getSalary(), actualManagers.get(1).getSalary());
    }

    @Test
    public void testFindAll_shouldReturnEmptyListWhenNoManagers() throws Exception {
        // Arrange
        List<Manager> expectedManagers = new ArrayList<>();

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeQuery methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mocking the next method of resultSet
        when(resultSetMock.next()).thenReturn(false);

        // Create the ManagerRepository instance
        ManagerRepository managerRepository = new ManagerRepository(connection);

        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertEquals(expectedManagers, actualManagers);
    }

    @Test
    public void testFindAll_shouldLogErrorIfSQLExceptionOccurs() throws Exception {
        // Arrange
        // Mocking the getConnection method of connectionToBase to throw SQLException
        when(connection.getConnection()).thenThrow(SQLException.class);

        // Create the ManagerRepository instance
        ManagerRepository managerRepository = new ManagerRepository(connection);

        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertEquals(Collections.emptyList(), actualManagers);
    }

    @Test
    public void testFindAll_shouldLogErrorIfClassNotFoundExceptionOccurs() throws Exception {
        // Arrange
        // Mocking the getConnection method of connectionToBase to throw ClassNotFoundException
        when(connection.getConnection()).thenThrow(ClassNotFoundException.class);

        // Create the ManagerRepository instance
        ManagerRepository managerRepository = new ManagerRepository(connection);

        // Act
        List<Manager> actualManagers = managerRepository.findAll();

        // Assert
        assertEquals(Collections.emptyList(), actualManagers);
    }

    @Test
    public void testSave_shouldSaveManagerSuccessfully() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        managerRepository.save(manager);

        // Assert
        verify(connection, times(1)).getConnection();
        verify(connectionMock, times(1)).prepareStatement(anyString());
        verify(statementMock, times(1)).setInt(eq(1), eq(1));
        verify(statementMock, times(1)).setString(eq(2), eq("John Doe"));
        verify(statementMock, times(1)).setBigDecimal(eq(3), eq(BigDecimal.valueOf(5000)));
        verify(statementMock, times(1)).executeUpdate();
    }

    @Test
    public void testSave_shouldLogErrorWhenSQLExceptionOccurs() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection to throw SQLException
        when(connectionMock.prepareStatement(anyString())).thenThrow(new SQLException("Mocked SQLException"));

        // Act
        managerRepository.save(manager);

        // Assert
        verify(connection, times(1)).getConnection();
        verify(connectionMock, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testSave_shouldLogErrorWhenClassNotFoundExceptionOccurs() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        // Mocking the getConnection method of connectionToBase to throw ClassNotFoundException
        when(connection.getConnection()).thenThrow(new ClassNotFoundException("Mocked ClassNotFoundException"));

        // Act
        managerRepository.save(manager);

        // Assert
        verify(connection, times(1)).getConnection();
    }

    @Test
    public void testUpdate_shouldUpdateManagerSuccessfully() throws Exception {
        // Arrange
        Manager manager = new Manager(1, "John Doe", BigDecimal.valueOf(5000));

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        managerRepository.update(manager);

        // Assert
        verify(connection, times(1)).getConnection();
        verify(connectionMock, times(1)).prepareStatement(anyString());
        verify(statementMock, times(1)).setString(eq(1), eq("John Doe"));
        verify(statementMock, times(1)).setBigDecimal(eq(2), eq(BigDecimal.valueOf(5000)));
        verify(statementMock, times(1)).setInt(eq(3), eq(1));
        verify(statementMock, times(1)).executeUpdate();
        verify(connectionMock, times(1)).close();
    }

    @Test
    public void testDeleteById_shouldDeleteManagerSuccessfully() throws Exception {
        // Arrange
        int id = 1;

        // Mocking the getConnection method of connectionToBase
        when(connection.getConnection()).thenReturn(connectionMock);

        // Mocking the prepareStatement and executeUpdate methods of connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(statementMock.executeUpdate()).thenReturn(1);

        // Act
        managerRepository.deleteById(id);

        // Assert
        verify(connection, times(1)).getConnection();
        verify(connectionMock, times(1)).prepareStatement(anyString());
        verify(statementMock, times(1)).setInt(1, id);
        verify(statementMock, times(1)).executeUpdate();
        verify(connectionMock, times(1)).close();
    }

}