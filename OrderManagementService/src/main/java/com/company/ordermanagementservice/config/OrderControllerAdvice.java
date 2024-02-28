package com.company.ordermanagementservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleGetException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleCreationException(RuntimeException ex) {
        log.error("An error occurred while creating a new order: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating new order");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUpdateException(Exception ex) {
        log.error("An error occurred while updating order: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error update order");
    }
}
