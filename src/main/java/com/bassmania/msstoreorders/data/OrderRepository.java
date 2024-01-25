package com.bassmania.msstoreorders.data;

import com.bassmania.msstoreorders.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    public List<Order> getOrders() {
        return orderJpaRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderJpaRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderJpaRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderJpaRepository.deleteById(id);
    }
}
