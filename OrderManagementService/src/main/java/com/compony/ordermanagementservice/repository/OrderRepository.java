package com.compony.ordermanagementservice.repository;

import com.compony.ordermanagementservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
