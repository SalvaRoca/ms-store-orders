package com.bassmania.msstoreorders.data;

import com.bassmania.msstoreorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
