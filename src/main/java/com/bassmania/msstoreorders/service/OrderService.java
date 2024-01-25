package com.bassmania.msstoreorders.service;

import com.bassmania.msstoreorders.model.Order;
import com.bassmania.msstoreorders.model.OrderRequest;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    Order getOrderById(String id);

    Order createOrder(OrderRequest orderRequest);


    Order updateOrder(Order existingOrder, String patchBody);

    void deleteOrder(String id);
}
