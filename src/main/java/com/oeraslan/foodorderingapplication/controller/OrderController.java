package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.OrderCreateOrUpdateDto;
import com.oeraslan.foodorderingapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createOrder(@RequestBody OrderCreateOrUpdateDto orderCreateDto) {
        log.info("[{}][createOrder] -> creating order", this.getClass().getSimpleName());

        orderService.createOrder(orderCreateDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@RequestBody OrderCreateOrUpdateDto orderUpdateDto, @PathVariable Long id) {
        log.info("[{}][updateOrder] -> updating order", this.getClass().getSimpleName());

        orderService.updateOrder(id,orderUpdateDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
        log.info("[{}][getOrderById] -> getting order", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrders() {
        log.info("[{}][getAllOrders] -> getting all orders", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) {
        log.info("[{}][deleteOrder] -> deleting order", this.getClass().getSimpleName());

        orderService.deleteOrder(id);
    }

    @GetMapping("/active")
    public ResponseEntity<Object> getActiveOrders() {
        log.info("[{}][getActiveOrders] -> getting active orders", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(orderService.getActiveOrders());
    }

}
