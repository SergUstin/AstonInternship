package com.company.ordermanagementservice.model;
/**
 * Перечисление, представляющее статус отправки.
 */
public enum ShipmentStatus {
    /**
     * Заказ еще не отправлен.
     */
    NOT_SHIPPED,

    /**
     * Заказ отправлен.
     */
    SHIPPED,

    /**
     * Заказ доставлен.
     */
    DELIVERED,

    /**
     * Отправка отменена.
     */
    CANCELED
}
