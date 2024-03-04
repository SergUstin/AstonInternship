package com.company.ordermanagementservice.dto;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigInteger;

/**
 * Класс представляет собой DTO для элемента заказа.
 */
@Data
public class OrderItemDTO {
    /**
     * Идентификатор элемента заказа.
     */
    private BigInteger id;

    /**
     * Наименование товара.
     */
    private String itemName;

    /**
     * Количество товара.
     */
    private int quantity;
}
