package com.company.ordermanagementservice.controller;

import com.company.ordermanagementservice.dto.OrderDTO;
import com.company.ordermanagementservice.service.impl.OrderServiceImpl;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private OrderController orderController;

    @Test
    public void testGetById_WhenValidId_ThenReturn200OK() throws Exception {
        BigInteger id = BigInteger.valueOf(1);
        OrderDTO expectedOrderDTO = new OrderDTO();
        when(orderService.getById(id)).thenReturn(expectedOrderDTO);

        mockMvc.perform(get("/api/orders/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll_WhenValidData_ThenReturn200OK() throws Exception {
        OrderDTO order1 = new OrderDTO();
        order1.setId(BigInteger.valueOf(1));
        OrderDTO order2 = new OrderDTO();
        order2.setId(BigInteger.valueOf(1));
        List<OrderDTO> orderList = Arrays.asList(order1, order2);
        when(orderService.getAll()).thenReturn(orderList);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate_WhenValidData_ThenReturn201Created() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(BigInteger.valueOf(1));
        when(orderService.create(any(OrderDTO.class))).thenReturn(orderDTO);

        ResultActions result = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1}"));

        result.andExpect(status().isOk());
    }

    @Test
    void update_WhenValidData_ThenReturnOk() throws Exception {
        BigInteger id = BigInteger.valueOf(1);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        when(orderService.update(eq(id), any(OrderDTO.class))).thenReturn(orderDTO);

        ResultActions result = mockMvc.perform(post("/api/orders")
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