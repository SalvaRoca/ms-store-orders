package com.bassmania.msstoreorders.service;

import com.bassmania.msstoreorders.data.OrderRepository;
import com.bassmania.msstoreorders.facade.ProductFacade;
import com.bassmania.msstoreorders.model.Order;
import com.bassmania.msstoreorders.model.OrderRequest;
import com.bassmania.msstoreorders.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductFacade productFacade;


    @Override
    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    @Override
    public Order getOrderById (String id) {
        return orderRepository.getOrderById(Long.valueOf(id));
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        List<Product> products = orderRequest.getProducts().stream().map(productFacade::getProduct).filter(Objects::nonNull).toList();

        if (products.size() != orderRequest.getProducts().size()) {
            return null;
        } else {
            Order order = Order.builder()
                    .date(java.time.LocalDate.now())
                    .products(products)
                    .total(products.stream().mapToDouble(Product::getPrice).sum())
                    .status("Confirmed")
                    .build();

            return orderRepository.saveOrder(order);
        }
    }

    @Override
    public Order updateOrder(Order order, String status) {
        if (status != null) {
            order.setStatus(status);
            return orderRepository.saveOrder(order);
        } else {
            return null;
        }
    }

    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteOrder(Long.valueOf(id));
    }
}
