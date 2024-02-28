package com.company.ordermanagementservice.service.impl;

import com.company.ordermanagementservice.dto.OrderItemDTO;
import com.company.ordermanagementservice.mapper.OrderMapper;
import com.company.ordermanagementservice.model.OrderItem;
import com.company.ordermanagementservice.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void testGetById_WhenValidId_ThenReturnOrderDTO() {
        BigInteger orderId = BigInteger.valueOf(123);
        OrderItem orderItem = new OrderItem();
        OrderItemDTO orderItemDTODTO = new OrderItemDTO();

        when(orderItemRepository.findById(orderId)).thenReturn(Optional.of(orderItem));
        when(orderMapper.toDTO(orderItem)).thenReturn(orderItemDTODTO);

        OrderItemDTO result = orderItemService.getById(orderId);

        assertEquals(orderItemDTODTO, result);
    }

    @Test
    void testGetById_WhenInvalidId_ThenThrowException() {
        BigInteger orderId = BigInteger.valueOf(123);

        when(orderItemRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderItemService.getById(orderId));
    }

    @Test
    void testGetAll_WhenOrdersExist_ThenReturnOrderDTOList() {
        OrderItem order1 = new OrderItem();
        OrderItem order2 = new OrderItem();
        List<OrderItem> orderList = Arrays.asList(order1, order2);
        OrderItemDTO orderDTO1 = new OrderItemDTO();
        OrderItemDTO orderDTO2 = new OrderItemDTO();
        List<OrderItemDTO> orderDTOList = Arrays.asList(orderDTO1, orderDTO2);

        when(orderItemRepository.findAll()).thenReturn(orderList);
        when(orderMapper.toDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toDTO(order2)).thenReturn(orderDTO2);

        List<OrderItemDTO> result = orderItemService.getAll();

        assertEquals(orderDTOList, result);
    }

    @Test
    void testCreate_WhenNonNullOrderDTO_ThenReturnOrderDTO() {
        OrderItemDTO orderDTO = new OrderItemDTO();
        OrderItem order = new OrderItem();

        when(orderMapper.toEntity(orderDTO)).thenReturn(order);
        when(orderItemRepository.save(order)).thenReturn(order);

        OrderItemDTO result = orderItemService.create(orderDTO);

        verify(orderMapper, times(1)).toEntity(orderDTO);
        verify(orderItemRepository, times(1)).save(order);
    }

    @Test
    void testCreate_WhenNullOrderDTO_ThenThrowIllegalArgumentException() {
        assertThrows(NullPointerException.class, () -> {
            orderItemService.create(null);
        });
    }

    @Test
    void testUpdate_WhenIdIsNull_ThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            orderItemService.update(null, new OrderItemDTO());
        });
    }

    @Test
    void testUpdate_WhenItemIsNull_ThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            orderItemService.update(BigInteger.valueOf(123), null);
        });
    }

    @Test
    void testUpdate_WhenOrderExists_ThenReturnUpdatedOrderDTO() {
        BigInteger orderId = BigInteger.valueOf(123);
        OrderItemDTO inputOrderDTO = new OrderItemDTO();
        inputOrderDTO.setId(orderId);
        OrderItem order = new OrderItem();
        when(orderItemRepository.existsById(orderId)).thenReturn(true);
        when(orderMapper.toEntity(inputOrderDTO)).thenReturn(order);
        when(orderItemRepository.save(order)).thenReturn(order);

        OrderItemDTO result = orderItemService.update(orderId, inputOrderDTO);

        verify(orderItemRepository).existsById(orderId);
        verify(orderItemRepository).save(order);
        assertEquals(inputOrderDTO, result);
    }

    @Test
    void testUpdate_WhenOrderDoesNotExist_ThenThrowNoSuchElementException() {
        // Вызов метода с несуществующим id
        BigInteger orderId = BigInteger.valueOf(456);
        OrderItemDTO inputOrderDTO = new OrderItemDTO();
        when(orderItemRepository.existsById(orderId)).thenReturn(false);

        // Проверка выбрасывания исключения
        assertThrows(NoSuchElementException.class, () -> {
            orderItemService.update(orderId, inputOrderDTO);
        });
    }

    @Test
    void testDelete_ShouldDeleteOrder_WhenOrderExists() {
        // Настройка входных данных и mock поведения
        BigInteger id = BigInteger.valueOf(1);
        when(orderItemRepository.existsById(id)).thenReturn(true);

        // Вызов тестируемого метода
        orderItemService.deleteById(id);

        // Проверка, что метод orderRepository.deleteById был вызван 1 раз с правильным id
        verify(orderItemRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete_ShouldThrowNoSuchElementException_WhenOrderNotExists() {
        BigInteger id = BigInteger.valueOf(1);
        when(orderItemRepository.existsById(id)).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderItemService.deleteById(id));
        assertEquals("Order not found with id: 1", exception.getMessage());
    }
}