// src/main/java/com/sliit/pharmacy/repository/OrderRepository.java
package com.sliit.pharmacy.repository;

import com.sliit.pharmacy.model.Order;
import com.sliit.pharmacy.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String email);
    List<Order> findByStatus(OrderStatus status);
    long countByStatus(OrderStatus status);
}