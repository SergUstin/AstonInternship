package com.company.ordermanagementservice.model;

/**
 * Перечисление, представляющее статус платежа.
 */
public enum PaymentStatus {
    /**
     * Платеж находится в ожидании.
     */
    PENDING,

    /**
     * Платеж завершен успешно.
     */
    COMPLETED,

    /**
     * Платеж отменён.
     */
    CANCELED
}
