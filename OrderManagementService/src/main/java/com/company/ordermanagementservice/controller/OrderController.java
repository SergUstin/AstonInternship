package com.company.ordermanagementservice.controller;

import com.company.ordermanagementservice.dto.OrderDTO;
import com.company.ordermanagementservice.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl orderService;
    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable BigInteger id) {
        log.info("GET /api/orders/{}", id);
        OrderDTO orderDTO = orderService.getById(id);
        return ResponseEntity.ok(orderDTO);
    }
    @GetMapping
    public ResponseEntity<Collection<OrderDTO>> getAll() {
        log.info("GET /api/orders");
        Collection<OrderDTO> orderDTOS = orderService.getAll();
        return ResponseEntity.ok(orderDTOS);
    }
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        log.info("POST /api/orders: {}", orderDTO);
        orderService.create(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable BigInteger id, @RequestBody OrderDTO orderDTO) {
        log.info("PUT /api/orders: {}", orderDTO);
        orderService.update(id, orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteById(@PathVariable BigInteger id) {
        log.info("DELETE /api/orders/{}", id);
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
