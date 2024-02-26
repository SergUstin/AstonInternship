package com.compony.ordermanagementservice.repository;

import com.compony.ordermanagementservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
