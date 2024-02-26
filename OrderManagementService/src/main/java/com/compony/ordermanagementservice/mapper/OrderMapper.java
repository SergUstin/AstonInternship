package com.compony.ordermanagementservice.mapper;

import com.compony.ordermanagementservice.dto.OrderDTO;
import com.compony.ordermanagementservice.dto.OrderItemDTO;
import com.compony.ordermanagementservice.model.Order;
import com.compony.ordermanagementservice.model.OrderItem;
import com.compony.ordermanagementservice.model.PaymentStatus;
import com.compony.ordermanagementservice.model.ShipmentStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        // Маппинг элементов заказа
        List<OrderItemDTO> itemDTOList = order.getItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        dto.setItems(itemDTOList);
        dto.setPaymentStatus(order.getPaymentStatus().name());
        dto.setShipmentStatus(order.getShipmentStatus().name());
        dto.setRegistrationDate(order.getRegistrationDate());
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUserId(dto.getUserId());
        // Преобразование элементов заказа в сущности
        List<OrderItem> itemList = dto.getItems().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        order.setItems(itemList);
        order.setShipmentStatus(ShipmentStatus.valueOf(dto.getShipmentStatus()));
        order.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));
        order.setRegistrationDate(dto.getRegistrationDate());
        return order;
    }

    public OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        // создаем объект Order
        Order order = new Order();
        order.setId(dto.getOrderId());
        item.setOrderId(order);
        item.setQuantity(dto.getQuantity());
        return item;
    }

    public OrderItemDTO toDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setOrderId(item.getOrderId().getId());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
