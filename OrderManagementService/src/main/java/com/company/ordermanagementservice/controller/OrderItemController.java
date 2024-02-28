package com.company.ordermanagementservice.controller;

import com.company.ordermanagementservice.dto.OrderDTO;
import com.company.ordermanagementservice.dto.OrderItemDTO;
import com.company.ordermanagementservice.service.impl.OrderItemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {
    private final OrderItemServiceImpl orderItemService;

    @Autowired
    public OrderItemController(OrderItemServiceImpl orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getById(@PathVariable BigInteger id) {
        log.info("GET /api/orderItems/{}", id);
        OrderItemDTO orderDTO = orderItemService.getById(id);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
    public ResponseEntity<Collection<OrderItemDTO>> getAll() {
        log.info("GET /api/orderItems");
        Collection<OrderItemDTO> orderDTOS = orderItemService.getAll();
        return ResponseEntity.ok(orderDTOS);
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> create(@RequestBody OrderItemDTO orderDTO) {
        log.info("POST /api/orderItems: {}", orderDTO);
        orderItemService.create(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> update(@PathVariable BigInteger id, @RequestBody OrderItemDTO orderDTO) {
        log.info("PUT /api/orderItems: {}", orderDTO);
        orderItemService.update(id, orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemDTO> deleteById(@PathVariable BigInteger id) {
        log.info("DELETE /api/orderItems/{}", id);
        orderItemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
