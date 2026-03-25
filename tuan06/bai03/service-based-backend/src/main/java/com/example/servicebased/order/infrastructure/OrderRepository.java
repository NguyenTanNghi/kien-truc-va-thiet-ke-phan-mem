package com.example.servicebased.order.infrastructure;

import com.example.servicebased.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
