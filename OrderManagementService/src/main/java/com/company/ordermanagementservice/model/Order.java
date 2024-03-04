package com.company.ordermanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Представляет объект заказа.
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    /**
     * Идентификатор пользователя, сделавшего заказ.
     */
    @Column(name = "user_id")
    private BigInteger userId;

    /**
     * Список идентификаторов товаров в заказе.
     */
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "item_id")
    private List<BigInteger> itemsId;

    /**
     * Статус оплаты заказа.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    /**
     * Статус отправки заказа.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "shipment_status")
    private ShipmentStatus shipmentStatus;

    /**
     * Дата регистрации заказа.
     */
    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDateTime registrationDate;
}