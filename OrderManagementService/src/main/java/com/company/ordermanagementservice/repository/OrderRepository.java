package com.company.ordermanagementservice.repository;

import com.company.ordermanagementservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
