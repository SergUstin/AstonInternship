package com.company.ordermanagementservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerAdviceTest {

    @Mock
    private IllegalArgumentException illegalArgumentException;

    @Mock
    private RuntimeException runtimeException;

    @Mock
    private Exception exception;

    @InjectMocks
    private OrderControllerAdvice orderControllerAdvice;

    @Test
    void handleGetException_ShouldReturnNotFound() {
        // Arrange
        String errorMessage = "Invalid order id";
        when(illegalArgumentException.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<String> response = orderControllerAdvice.handleGetException(illegalArgumentException);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
        verify(illegalArgumentException, times(1)).getMessage();
    }

    @Test
    void handleCreationException_ShouldReturnBadRequest() {
        // Arrange
        String errorMessage = "Error creating new order";
        when(runtimeException.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<String> response = orderControllerAdvice.handleCreationException(runtimeException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error creating new order", response.getBody());
        verify(runtimeException, times(1)).getMessage();
    }

    @Test
    void handleUpdateException_ShouldReturnNotFound() {
        // Arrange
        String errorMessage = "Error update order";
        when(exception.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<String> response = orderControllerAdvice.handleUpdateException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error update order", response.getBody());
        verify(exception, times(1)).getMessage();
    }

}