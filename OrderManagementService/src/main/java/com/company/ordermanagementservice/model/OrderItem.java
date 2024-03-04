package com.company.ordermanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Представляет элемент заказа.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    /**
     * Уникальный идентификатор элемента заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    /**
     * Наименование товара.
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * Количество товара.
     */
    private int quantity;
}
