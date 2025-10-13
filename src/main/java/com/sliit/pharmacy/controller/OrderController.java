// src/main/java/com/sliit/pharmacy/controller/OrderController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.Order;
import com.sliit.pharmacy.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/buy/{medicineId}")
    public String buyMedicine(@PathVariable Long medicineId,
                              @RequestParam(defaultValue = "1") int quantity,
                              Authentication auth) {
        orderService.createOrder(medicineId, quantity, auth.getName());
        return "redirect:/orders?created";
    }

    @GetMapping
    public String listOrders(Authentication auth, Model model) {
        model.addAttribute("orders", orderService.getOrdersByCustomer(auth.getName()));
        return "orders/list";
    }

    @GetMapping("/checkout/{orderId}")
    public String checkout(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orders/checkout";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable Long orderId) {
        orderService.processPayment(orderId);
        return "redirect:/orders?paid";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, Authentication auth) {
        orderService.cancelOrder(orderId, auth.getName());
        return "redirect:/orders?cancelled";
    }

    /**
     * View invoice with discount calculation
     */
    @GetMapping("/invoice/{orderId}")
    public String viewInvoice(@PathVariable Long orderId, Authentication auth, Model model) {
        Order order = orderService.getOrderById(orderId);

        // Ensure user can only view their own order
        if (!order.getCustomer().getEmail().equals(auth.getName())) {
            return "redirect:/orders";
        }

        // Calculate discount
        BigDecimal discountedTotal = order.getDiscountedTotal(); // already calculated in OrderService
        if (discountedTotal == null) {
            discountedTotal = order.getTotalAmount(); // fallback if no discount applied
        }
        BigDecimal discountAmount = order.getTotalAmount().subtract(discountedTotal);

        // Add attributes to model for Thymeleaf
        model.addAttribute("order", order);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("finalTotal", discountedTotal);

        return "orders/invoice";
    }

    @GetMapping("/pending")
    public String pendingOrders(Model model) {
        model.addAttribute("orders", orderService.getPendingOrders());
        return "orders/pending";
    }

    @PostMapping("/admin/approve/{orderId}")
    public String approveOrder(@PathVariable Long orderId) {
        orderService.approveOrder(orderId);
        return "redirect:/orders/pending?approved";
    }

    @PostMapping("/admin/mark-paid/{orderId}")
    public String markOrderAsPaid(@PathVariable Long orderId) {
        orderService.processPayment(orderId);
        return "redirect:/orders/pending?paid";
    }
}