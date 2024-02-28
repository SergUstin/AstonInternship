package com.company.ordermanagementservice.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private BigInteger id;
    private BigInteger userId;
    private List<OrderItemDTO> items;
    private String paymentStatus;
    private String shipmentStatus;
}
