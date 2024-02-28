package com.company.ordermanagementservice.service.impl;

import com.company.ordermanagementservice.dto.OrderItemDTO;
import com.company.ordermanagementservice.mapper.OrderMapper;
import com.company.ordermanagementservice.model.OrderItem;
import com.company.ordermanagementservice.repository.OrderItemRepository;
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
public class OrderItemServiceImpl implements ServiceMethod<OrderItemDTO> {
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderMapper orderMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderItemDTO getById(BigInteger id) {
        Objects.requireNonNull(id, "Id must not be null");
        return orderItemRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
    }

    @Override
    public List<OrderItemDTO> getAll() {
        List<OrderItem> orders = orderItemRepository.findAll();
        log.info("Retrieved {} orders from the database", orders.size());
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO create(OrderItemDTO item) {
        Objects.requireNonNull(item, "OrderDTO must not be null");

        OrderItem order = orderMapper.toEntity(item);
        if (order == null) {
            throw new IllegalArgumentException("Error mapping OrderDTO to Order entity");
        }

        OrderItem savedOrder = orderItemRepository.save(order);

        log.info("Created new order with id: {}", savedOrder.getId());
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderItemDTO update(BigInteger id, OrderItemDTO item) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(item, "OrderDTO must not be null");
        if (orderItemRepository.existsById(id)) {
            OrderItem order = orderMapper.toEntity(item);
            OrderItem updatedOrder = orderItemRepository.save(order);
            log.info("Updated order with id: {}", updatedOrder.getId());
        } else {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
        return item;
    }

    @Override
    public void deleteById(BigInteger id) {
        Objects.requireNonNull(id, "Id must not be null");
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            log.info("Deleted order with id: {}", id);
        } else {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
    }
}
