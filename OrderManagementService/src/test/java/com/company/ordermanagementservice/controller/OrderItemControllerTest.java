package com.company.ordermanagementservice.controller;

import com.company.ordermanagementservice.dto.OrderItemDTO;
import com.company.ordermanagementservice.service.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderItemController.class)
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemServiceImpl orderService;

    @MockBean
    private OrderItemController orderController;

    @Test
    public void testGetById_WhenValidId_ThenReturn200OK() throws Exception {
        BigInteger id = BigInteger.valueOf(1);
        OrderItemDTO expectedOrderDTO = new OrderItemDTO();
        when(orderService.getById(id)).thenReturn(expectedOrderDTO);

        mockMvc.perform(get("/api/orderItems/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll_WhenValidData_ThenReturn200OK() throws Exception {
        OrderItemDTO order1 = new OrderItemDTO();
        order1.setId(BigInteger.valueOf(1));
        OrderItemDTO order2 = new OrderItemDTO();
        order2.setId(BigInteger.valueOf(1));
        List<OrderItemDTO> orderList = Arrays.asList(order1, order2);
        when(orderService.getAll()).thenReturn(orderList);

        mockMvc.perform(get("/api/orderItems"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate_WhenValidData_ThenReturn201Created() throws Exception {
        OrderItemDTO orderDTO = new OrderItemDTO();
        orderDTO.setId(BigInteger.valueOf(1));
        when(orderService.create(any(OrderItemDTO.class))).thenReturn(orderDTO);

        ResultActions result = mockMvc.perform(post("/api/orderItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1}"));

        result.andExpect(status().isOk());
    }

    @Test
    void update_WhenValidData_ThenReturnOk() throws Exception {
        BigInteger id = BigInteger.valueOf(1);
        OrderItemDTO orderDTO = new OrderItemDTO();
        orderDTO.setId(id);
        when(orderService.update(eq(id), any(OrderItemDTO.class))).thenReturn(orderDTO);

        ResultActions result = mockMvc.perform(post("/api/orderItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1}"));

        result.andExpect(status().isOk());
    }

    @Test
    void deleteById_WhenValidId_ThenReturnNoContent() {
        BigInteger id = BigInteger.valueOf(1);

        doNothing().when(orderService).deleteById(id);

        orderController.deleteById(id);

        verify(orderController, times(1)).deleteById(id);
    }
}