// src/main/java/com/sliit/pharmacy/service/OrderService.java
package com.sliit.pharmacy.service;

import com.sliit.pharmacy.model.Medicine;
import com.sliit.pharmacy.model.Order;
import com.sliit.pharmacy.model.OrderItem;
import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MedicineService medicineService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository,
                        MedicineService medicineService,
                        UserService userService) {
        this.orderRepository = orderRepository;
        this.medicineService = medicineService;
        this.userService = userService;
    }

    @Transactional
    public Order createOrder(Long medicineId, int quantity, String customerEmail) {
        Medicine medicine = medicineService.getMedicineById(medicineId);
        User customer = userService.findUserByEmail(customerEmail);

        if (medicine == null) {
            throw new RuntimeException("Medicine not found with ID: " + medicineId);
        }

        if (medicine.getQuantity() == null || medicine.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for " + medicine.getName());
        }

        // ✅ Deduct stock
        medicine.setQuantity(medicine.getQuantity() - quantity);
        medicineService.saveMedicine(medicine);

        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.valueOf(medicine.getPrice()).multiply(BigDecimal.valueOf(quantity)));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setMedicine(medicine);
        item.setQuantity(quantity);
        item.setPricePerUnit(BigDecimal.valueOf(medicine.getPrice()));
        order.getItems().add(item);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public void cancelOrder(Long orderId, String customerEmail) {
        Order order = getOrderById(orderId);
        if (!order.getCustomer().getEmail().equals(customerEmail)) {
            throw new RuntimeException("Not authorized to cancel this order");
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new RuntimeException("Cannot cancel non-pending order");
        }

        // ✅ Restore stock when order is cancelled
        for (OrderItem item : order.getItems()) {
            Medicine medicine = item.getMedicine();
            medicine.setQuantity(medicine.getQuantity() + item.getQuantity());
            medicineService.saveMedicine(medicine);
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setApproved(true);
        orderRepository.save(order);
    }

    @Transactional
    public void processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus(Order.OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.PENDING);
    }

    public long countPendingOrders() {
        return orderRepository.countByStatus(Order.OrderStatus.PENDING);
    }
}
