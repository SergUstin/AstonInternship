package com.company.ordermanagementservice.dto;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigInteger;

@Data
public class OrderItemDTO {
    private BigInteger id;
    private String itemName;
    private int quantity;
}
