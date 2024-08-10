package com.oeraslan.foodorderingapplication.repository;

import com.oeraslan.foodorderingapplication.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE status IN ('RECEIVED', 'PREPARING', 'READY') AND deleted = false", nativeQuery = true)
    public List<Order> getActiveOrders();

}
