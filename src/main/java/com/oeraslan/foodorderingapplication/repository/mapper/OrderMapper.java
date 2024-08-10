package com.oeraslan.foodorderingapplication.repository.mapper;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.repository.entity.Order;

import java.util.Date;
import java.util.Optional;

public interface OrderMapper {

    static Order createOrder(OrderCreateOrUpdateDto orderDto) {
        return Order.builder()
                .dinnerTableId(orderDto.getDinnerTableId())
                .foodList(orderDto.getFoods())
                .status(Status.RECEIVED)
                .deleted(false)
                .orderCreateDate(new Date())
                .updatedDate(new Date())
                .build();
    };

    static Order updateOrder(Order order, OrderCreateOrUpdateDto orderDto) {
        Optional.ofNullable(orderDto.getDinnerTableId())
                .ifPresent(order::setDinnerTableId);
        Optional.ofNullable(orderDto.getFoods())
                .filter(foods -> !foods.isEmpty())
                .ifPresent(order::setFoodList);
        Optional.ofNullable(orderDto.getStatus())
                .ifPresent(order::setStatus);
        order.setUpdatedDate(new Date());

        return order;
    };

    static OrderResponseDto orderToOrderResponseDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .dinnerTableId(order.getDinnerTableId())
                .foods(order.getFoodList())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .orderCreatedDate(order.getOrderCreateDate().toString())
                .updatedDate(order.getUpdatedDate().toString())
                .build();
    };
}
