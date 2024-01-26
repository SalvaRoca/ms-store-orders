package com.bassmania.msstoreorders.controller;

import com.bassmania.msstoreorders.model.Order;
import com.bassmania.msstoreorders.model.OrderRequest;
import com.bassmania.msstoreorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orderList = orderService.getOrders();
        if (!orderList.isEmpty()) {
            return ResponseEntity.ok(orderList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("{\"errorMessage\": \"Some input data might be incorrect, please check and try again\"}");
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> patchOrder(@PathVariable String id, @RequestParam String status) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            Order updatedProduct = orderService.updateOrder(existingOrder, status);

            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.badRequest().body("{\"errorMessage\": \"Some input data might be incorrect, please check and try again\"}");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        Order order = orderService.getOrderById(id);

        if (order != null) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
