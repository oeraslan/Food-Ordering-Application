package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private OrderCreateOrUpdateDto orderCreateOrUpdateDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        orderCreateOrUpdateDto = OrderCreateOrUpdateDto.builder()
                .dinnerTableId(1L)
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .dinnerTableId(1L)
                .build();
    }

    @Test
    void testCreateOrder() {
        //when
        doNothing().when(orderService).createOrder(any(OrderCreateOrUpdateDto.class));
        //then
        orderController.createOrder(orderCreateOrUpdateDto);

        verify(orderService, times(1)).createOrder(any(OrderCreateOrUpdateDto.class));
    }

    @Test
    void testUpdateOrder() {
        doNothing().when(orderService).updateOrder(anyLong(), any(OrderCreateOrUpdateDto.class));

        orderController.updateOrder(orderCreateOrUpdateDto, 1L);

        verify(orderService, times(1)).updateOrder(anyLong(), any(OrderCreateOrUpdateDto.class));
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById(anyLong())).thenReturn(orderResponseDto);

        ResponseEntity<Object> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).getOrderById(anyLong());
        assertInstanceOf(OrderResponseDto.class, response.getBody());
    }

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(orderResponseDto));

        ResponseEntity<Object> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder(anyLong());

        orderController.deleteOrder(1L);

        verify(orderService, times(1)).deleteOrder(anyLong());
    }

    @Test
    void testGetActiveOrders() {
        when(orderService.getActiveOrders()).thenReturn(List.of(orderResponseDto));

        ResponseEntity<Object> response = orderController.getActiveOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).getActiveOrders();
    }

}
