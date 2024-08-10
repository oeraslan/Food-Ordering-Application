package com.oeraslan.foodorderingapplication.service;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    void createOrder(OrderCreateOrUpdateDto orderCreateDto);

    void updateOrder(OrderCreateOrUpdateDto orderUpdateDto);

    void deleteOrder(Long id);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getAllOrders();

    List<OrderResponseDto> getActiveOrders();

}
