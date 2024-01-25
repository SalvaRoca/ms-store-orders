package com.bassmania.msstoreorders.service;

import com.bassmania.msstoreorders.data.OrderRepository;
import com.bassmania.msstoreorders.facade.ProductFacade;
import com.bassmania.msstoreorders.model.Order;
import com.bassmania.msstoreorders.model.OrderRequest;
import com.bassmania.msstoreorders.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
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

    @Autowired
    private ObjectMapper objectMapper;

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
    public Order updateOrder(Order existingOrder, String patchBody) {
        try {
            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(patchBody));
            JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(existingOrder)));
            Order patched = objectMapper.treeToValue(target, Order.class);
            orderRepository.saveOrder(patched);
            return patched;
        } catch (JsonProcessingException | JsonPatchException e) {
            return null;
        }
    }

    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteOrder(Long.valueOf(id));
    }
}
