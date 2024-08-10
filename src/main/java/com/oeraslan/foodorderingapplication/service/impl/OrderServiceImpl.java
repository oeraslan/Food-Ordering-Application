package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.repository.FoodRepository;
import com.oeraslan.foodorderingapplication.repository.OrderRepository;
import com.oeraslan.foodorderingapplication.repository.entity.Order;
import com.oeraslan.foodorderingapplication.repository.mapper.OrderMapper;
import com.oeraslan.foodorderingapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final FoodRepository foodRepository;

    @Override
    public void createOrder(OrderCreateOrUpdateDto orderCreateDto) {
        log.info("[{}][createOrder] -> request: {}", this.getClass().getSimpleName(), orderCreateDto);

        Order order = OrderMapper.createOrder(orderCreateDto);
        order.setTotalPrice(calculateTotalPrice(orderCreateDto.getFoods()));
        orderRepository.save(order);

        log.info("[{}][createOrder] -> order created: {}", this.getClass().getSimpleName(), order);
    }

    @Override
    public void updateOrder(Long id, OrderCreateOrUpdateDto orderUpdateDto) {
        log.info("[{}][updateOrder] -> request: {}", this.getClass().getSimpleName(), orderUpdateDto);

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        Order updatedOrder = OrderMapper.updateOrder(order, orderUpdateDto);
        updatedOrder.setTotalPrice(calculateTotalPrice(orderUpdateDto.getFoods()));
        orderRepository.save(updatedOrder);

        log.info("[{}][updateOrder] -> order updated: {}", this.getClass().getSimpleName(), updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        log.info("[{}][deleteOrder] -> request id: {}", this.getClass().getSimpleName(), id);

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setDeleted(true);
        orderRepository.save(order);

        log.info("[{}][deleteOrder] -> order deleted: {}", this.getClass().getSimpleName(), order);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        log.info("[{}][getOrderById] -> request id: {}", this.getClass().getSimpleName(), id);

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderMapper.orderToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        log.info("[{}][getAllOrders] -> request all orders", this.getClass().getSimpleName());

        return orderRepository.findAll().stream().map(OrderMapper::orderToOrderResponseDto).toList();
    }

    @Override
    public List<OrderResponseDto> getActiveOrders() {
        log.info("[{}][getActiveOrders] -> request active orders", this.getClass().getSimpleName());

        return orderRepository.getActiveOrders().stream().map(OrderMapper::orderToOrderResponseDto).toList();
    }

    private double calculateTotalPrice(Map<Long,Integer> foods) {
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        foods.forEach((foodId, quantity) -> foodRepository.findById(foodId).ifPresent(food -> totalPrice.updateAndGet(v -> v + food.getPrice() * quantity)));
        return totalPrice.get();
    }

}
