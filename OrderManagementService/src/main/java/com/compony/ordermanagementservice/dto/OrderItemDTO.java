package com.compony.ordermanagementservice.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private int quantity;
}
