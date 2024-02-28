package com.company.ordermanagementservice.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class OrderItemDTO {
    private BigInteger id;
    private BigInteger orderId;
    private int quantity;
}
