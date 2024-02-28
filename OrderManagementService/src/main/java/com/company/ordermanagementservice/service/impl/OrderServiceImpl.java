package com.company.ordermanagementservice.service.impl;

import com.company.ordermanagementservice.dto.OrderDTO;
import com.company.ordermanagementservice.mapper.OrderMapper;
import com.company.ordermanagementservice.model.Order;
import com.company.ordermanagementservice.repository.OrderRepository;
import com.company.ordermanagementservice.service.ServiceMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements ServiceMethod<OrderDTO> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    @Override
    public OrderDTO getById(BigInteger id) {
        Objects.requireNonNull(id, "Id must not be null");
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
    }
    @Override
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        log.info("Retrieved {} orders from the database", orders.size());
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public OrderDTO create(OrderDTO item) {
        Objects.requireNonNull(item, "OrderDTO must not be null");

        Order order = orderMapper.toEntity(item);
        if (order == null) {
            throw new IllegalArgumentException("Error mapping OrderDTO to Order entity");
        }

        Order savedOrder = orderRepository.save(order);

        log.info("Created new order with id: {}", savedOrder.getId());
        return orderMapper.toDTO(savedOrder);
    }
    @Override
    public OrderDTO update(BigInteger id, OrderDTO item) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(item, "OrderDTO must not be null");
        if (orderRepository.existsById(id)) {
            Order order = orderMapper.toEntity(item);
            Order updatedOrder = orderRepository.save(order);
            log.info("Updated order with id: {}", updatedOrder.getId());
        } else {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
        return item;
    }
    @Override
    public void deleteById(BigInteger id) {
        Objects.requireNonNull(id, "Id must not be null");
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("Deleted order with id: {}", id);
        } else {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
    }
}
