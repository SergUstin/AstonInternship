package com.company.ordermanagementservice.service.impl;

import com.company.ordermanagementservice.dto.OrderDTO;
import com.company.ordermanagementservice.mapper.OrderMapper;
import com.company.ordermanagementservice.model.Order;
import com.company.ordermanagementservice.repository.OrderRepository;
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
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testGetById_WhenValidId_ThenReturnOrderDTO() {
        BigInteger orderId = BigInteger.valueOf(123);
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.getById(orderId);

        assertEquals(orderDTO, result);
    }

    @Test
    void testGetById_WhenInvalidId_ThenThrowException() {
        BigInteger orderId = BigInteger.valueOf(123);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.getById(orderId));
    }

    @Test
    void testGetAll_WhenOrdersExist_ThenReturnOrderDTOList() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orderList = Arrays.asList(order1, order2);
        OrderDTO orderDTO1 = new OrderDTO();
        OrderDTO orderDTO2 = new OrderDTO();
        List<OrderDTO> orderDTOList = Arrays.asList(orderDTO1, orderDTO2);

        when(orderRepository.findAll()).thenReturn(orderList);
        when(orderMapper.toDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toDTO(order2)).thenReturn(orderDTO2);

        List<OrderDTO> result = orderService.getAll();

        assertEquals(orderDTOList, result);
    }

    @Test
    void testCreate_WhenNonNullOrderDTO_ThenReturnOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();

        when(orderMapper.toEntity(orderDTO)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        OrderDTO result = orderService.create(orderDTO);

        verify(orderMapper, times(1)).toEntity(orderDTO);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreate_WhenNullOrderDTO_ThenThrowIllegalArgumentException() {
        assertThrows(NullPointerException.class, () -> {
            orderService.create(null);
        });
    }

    @Test
    void testUpdate_WhenIdIsNull_ThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            orderService.update(null, new OrderDTO());
        });
    }

    @Test
    void testUpdate_WhenItemIsNull_ThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            orderService.update(BigInteger.valueOf(123), null);
        });
    }

    @Test
    void testUpdate_WhenOrderExists_ThenReturnUpdatedOrderDTO() {
        BigInteger orderId = BigInteger.valueOf(123);
        OrderDTO inputOrderDTO = new OrderDTO();
        inputOrderDTO.setId(orderId);
        Order order = new Order();
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderMapper.toEntity(inputOrderDTO)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        OrderDTO result = orderService.update(orderId, inputOrderDTO);

        verify(orderRepository).existsById(orderId);
        verify(orderRepository).save(order);
        assertEquals(inputOrderDTO, result);
    }

    @Test
    void testUpdate_WhenOrderDoesNotExist_ThenThrowNoSuchElementException() {
        // Вызов метода с несуществующим id
        BigInteger orderId = BigInteger.valueOf(456);
        OrderDTO inputOrderDTO = new OrderDTO();
        when(orderRepository.existsById(orderId)).thenReturn(false);

        // Проверка выбрасывания исключения
        assertThrows(NoSuchElementException.class, () -> {
            orderService.update(orderId, inputOrderDTO);
        });
    }

    @Test
    void testDelete_ShouldDeleteOrder_WhenOrderExists() {
        // Настройка входных данных и mock поведения
        BigInteger id = BigInteger.valueOf(1);
        when(orderRepository.existsById(id)).thenReturn(true);

        // Вызов тестируемого метода
        orderService.deleteById(id);

        // Проверка, что метод orderRepository.deleteById был вызван 1 раз с правильным id
        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete_ShouldThrowNoSuchElementException_WhenOrderNotExists() {
        BigInteger id = BigInteger.valueOf(1);
        when(orderRepository.existsById(id)).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> orderService.deleteById(id));
        assertEquals("Order not found with id: 1", exception.getMessage());
    }
}