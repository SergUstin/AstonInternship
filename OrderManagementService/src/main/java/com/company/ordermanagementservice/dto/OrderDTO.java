package com.company.ordermanagementservice.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * Класс представляет собой DTO для объекта заказа.
 */
@Data
public class OrderDTO {
    /**
     * Идентификатор заказа.
     */
    private BigInteger id;

    /**
     * Идентификатор пользователя, сделавшего заказ.
     */
    private BigInteger userId;

    /**
     * Список идентификаторов товаров в заказе.
     */
    private List<BigInteger> itemsId;

    /**
     * Статус оплаты заказа.
     */
    private String paymentStatus;

    /**
     * Статус доставки заказа.
     */
    private String shipmentStatus;
}
