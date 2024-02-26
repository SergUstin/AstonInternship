package com.company.ordermanagementservice.repository;

import com.company.ordermanagementservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
