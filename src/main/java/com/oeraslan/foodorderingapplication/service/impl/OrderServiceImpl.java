package com.oeraslan.foodorderingapplication.service.impl;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.dto.OrderResponseDto;
import com.oeraslan.foodorderingapplication.enums.Category;
import com.oeraslan.foodorderingapplication.enums.Status;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderAlreadyDeletedException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotFoundException;
import com.oeraslan.foodorderingapplication.exception.exceptions.OrderNotUpdatedException;
import com.oeraslan.foodorderingapplication.repository.FoodRepository;
import com.oeraslan.foodorderingapplication.repository.OrderRepository;
import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import com.oeraslan.foodorderingapplication.repository.entity.Order;
import com.oeraslan.foodorderingapplication.repository.mapper.DinnerTableMapper;
import com.oeraslan.foodorderingapplication.repository.mapper.OrderMapper;
import com.oeraslan.foodorderingapplication.service.DinnerTableService;
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

    private final DinnerTableService dinnerTableService;

    @Override
    public void createOrder(OrderCreateOrUpdateDto orderCreateDto) {
        log.info("[{}][createOrder] -> request: {}", this.getClass().getSimpleName(), orderCreateDto);

        try {

            Order order = OrderMapper.createOrder(orderCreateDto);
            order.setTotalPrice(calculateTotalPrice(orderCreateDto.getFoods()));
            Order response = orderRepository.save(order);

            updateDinnerTable(orderCreateDto.getDinnerTableId(), response.getId(), Status.RESERVED);

            log.info("[{}][createOrder] -> order created: {}", this.getClass().getSimpleName(), order);
        } catch (Exception e) {

            log.error("[{}][createOrder] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new OrderNotFoundException("Order not created: " + e.getMessage());
        }

    }


    @Override
    public void updateOrder(Long id, OrderCreateOrUpdateDto orderUpdateDto) {
        log.info("[{}][updateOrder] -> request: {}", this.getClass().getSimpleName(), orderUpdateDto);

        Order order = findOrderById(id);

        try {

            Order updatedOrder = OrderMapper.updateOrder(order, orderUpdateDto);
            updatedOrder.setTotalPrice(calculateTotalPrice(orderUpdateDto.getFoods()));
            orderRepository.save(updatedOrder);

            updateDinnerTable(orderUpdateDto.getDinnerTableId(), id, orderUpdateDto.getStatus());

            log.info("[{}][updateOrder] -> order updated: {}", this.getClass().getSimpleName(), updatedOrder);
        } catch (Exception e) {

            log.error("[{}][updateOrder] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new OrderNotUpdatedException("Order not updated with id: " + id + ", error: " + e.getMessage());
        }

    }

    @Override
    public void deleteOrder(Long id) {
        log.info("[{}][deleteOrder] -> request id: {}", this.getClass().getSimpleName(), id);

        Order order = findOrderById(id);

        if (order.isDeleted()) {
            throw new OrderAlreadyDeletedException("Order already deleted with id: " + id);
        }

        try {
            order.setDeleted(true);
            orderRepository.save(order);

            log.info("[{}][deleteOrder] -> order deleted: {}", this.getClass().getSimpleName(), order);
        } catch (Exception e) {

            log.error("[{}][deleteOrder] -> error: {}", this.getClass().getSimpleName(), e.getMessage());
            throw new OrderNotUpdatedException("Order not deleted with id: " + id + ", error: " + e.getMessage());
        }

    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        log.info("[{}][getOrderById] -> request id: {}", this.getClass().getSimpleName(), id);

        Order order = findOrderById(id);

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

    private double calculateTotalPrice(Map<Long, Integer> foods) {
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        foods.forEach((foodId, quantity) -> foodRepository.findById(foodId).ifPresent(food -> totalPrice.updateAndGet(v -> v + food.getPrice() * quantity)));
        return totalPrice.get();
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    private void updateDinnerTable(Long tableId, Long orderId, Status status) {
        log.info("[{}][updateDinnerTable] -> tableId: {}, orderId: {}", this.getClass().getSimpleName(), tableId, orderId);

        DinnerTable dinnerTable = dinnerTableService.findDinnerTableById(tableId);
        List<Long> orderIds = dinnerTable.getOrderIds();
        if (!orderIds.contains(orderId)) {
            orderIds.add(orderId);
        }
        dinnerTable.setOrderIds(orderIds);
        dinnerTable.setStatus(status);

        DinnerTableReserveDto dinnerTableReserveDto = DinnerTableMapper.dinnerTableToDinnerTableReserveDto(dinnerTable);

        dinnerTableService.updateDinnerTable(tableId, dinnerTableReserveDto);

        log.info("[{}][updateDinnerTable] -> dinner table updated: {}", this.getClass().getSimpleName(), dinnerTable);
    }

}
