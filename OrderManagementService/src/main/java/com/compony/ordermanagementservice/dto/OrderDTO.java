package com.compony.ordermanagementservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private String paymentStatus;
    private String shipmentStatus;
    private LocalDateTime registrationDate;
}
