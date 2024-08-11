package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotCreatedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotUpdatedException;
import com.oeraslan.foodorderingapplication.repository.FoodRepository;
import com.oeraslan.foodorderingapplication.repository.OrderRepository;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import com.oeraslan.foodorderingapplication.repository.entity.Food;
import com.oeraslan.foodorderingapplication.repository.entity.Order;
import com.oeraslan.foodorderingapplication.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private DinnerTableService dinnerTableService;

    private Order order;
    private Order order2;
    private Food food;
    private DinnerTable dinnerTable;
    private OrderCreateOrUpdateDto orderCreateOrUpdateDto;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setDinnerTableId(1L);
        order.setFoodList(Map.of(1L, 2));
        order.setStatus(Status.RECEIVED);
        order.setDeleted(false);
        order.setOrderCreateDate(new Date());
        order.setUpdatedDate(new Date());

        order2 = new Order();
        order2.setId(2L);
        order2.setDinnerTableId(1L);
        order2.setFoodList(Map.of(1L, 5));
        order2.setStatus(Status.RECEIVED);
        order2.setOrderCreateDate(new Date());
        order2.setUpdatedDate(new Date());

        food = new Food();
        food.setId(1L);
        food.setName("Test Food");
        food.setPrice(10.0);
        food.setCategory(Category.MAIN_COURSE);

        dinnerTable = new DinnerTable();
        dinnerTable.setId(1L);
        dinnerTable.setOrderIds(List.of(1L));
        dinnerTable.setStatus(Status.NOT_RESERVED);

        orderCreateOrUpdateDto = OrderCreateOrUpdateDto.builder()
                .dinnerTableId(1L)
                .foods(Map.of(1L, 3))
                .status(Status.RECEIVED)
                .build();

    }

    @Test
    void whenCreateOrderCalledWithDto_thenSaveOrder() {
        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(dinnerTableService.findDinnerTableById(1L)).thenReturn(dinnerTable);
        // then
        orderService.createOrder(orderCreateOrUpdateDto);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void whenCreateOrderCalled_thenThrowException() {
        // when
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(OrderNotCreatedException.class, () -> orderService.createOrder(orderCreateOrUpdateDto));
    }

    @Test
    void whenUpdateOrderCalledWithDto_thenUpdateOrder() {
        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(dinnerTableService.findDinnerTableById(1L)).thenReturn(dinnerTable);
        // then
        orderService.updateOrder(1L, orderCreateOrUpdateDto);
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order.getFoodList().get(1L), orderCreateOrUpdateDto.getFoods().get(1L));
    }

    @Test
    void whenUpdateOrderCalled_thenThrowException() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(OrderNotUpdatedException.class, () -> orderService.updateOrder(1L, orderCreateOrUpdateDto));
    }

    @Test
    void whenDeleteOrderCalled_thenDeleteOrder() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        // then
        orderService.deleteOrder(1L);
        assertTrue(order.isDeleted());
    }

    @Test
    void whenDeleteOrderCalled_thenThrowOrderAlreadyDeletedException() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        order.setDeleted(true);
        // then
        assertThrows(OrderAlreadyDeletedException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void whenDeleteOrderCalled_thenThrowOrderNotUpdatedException() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException());
        // then
        assertThrows(OrderNotUpdatedException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void whenGetOrderByIdCalled_thenGetOrder() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        // then
        OrderResponseDto orderResponseDto = orderService.getOrderById(1L);
        verify(orderRepository, times(1)).findById(1L);
        assertEquals(order.getId(), orderResponseDto.getId());
    }

    @Test
    void whenGetOrderByIdCalled_thenThrowException() {
        // when
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // then
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void whenGetAllOrdersCalled_thenReturnAllOrders() {
        // when
        when(orderRepository.findAll()).thenReturn(List.of(order, order2));
        // then
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        assertEquals(2, orderResponseDtos.size());
    }

    @Test
    void whenGetActiveOrdersCalled_thenReturnActiveOrders() {
        // when
        when(orderRepository.getActiveOrders()).thenReturn(List.of(order, order2));
        // then
        List<OrderResponseDto> orderResponseDtos = orderService.getActiveOrders();
        assertEquals(2, orderResponseDtos.size());
    }

}
