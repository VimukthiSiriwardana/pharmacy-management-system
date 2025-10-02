// src/main/java/com/sliit/pharmacy/controller/AdminOrderController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ View all pending orders
    @GetMapping("/pending")
    public String pendingOrders(Model model) {
        model.addAttribute("orders", orderService.getPendingOrders());
        return "admin/orders/pending";  // points to src/main/resources/templates/admin/orders/pending.html
    }

    // ✅ Approve order (POST request with CSRF token in form)
    @PostMapping("/approve/{orderId}")
    public String approveOrder(@PathVariable Long orderId) {
        try {
            orderService.approveOrder(orderId);
            return "redirect:/admin/orders/pending?approved";
        } catch (Exception e) {
            return "redirect:/admin/orders/pending?error";
        }
    }

    // ✅ Mark order as paid (POST request with CSRF token in form)
    @PostMapping("/mark-paid/{orderId}")
    public String markAsPaid(@PathVariable Long orderId) {
        try {
            orderService.processPayment(orderId);
            return "redirect:/admin/orders/pending?paid";
        } catch (Exception e) {
            return "redirect:/admin/orders/pending?error";
        }
    }

    // ❌ (Optional) If you want to allow GET testing instead of POST (only for development!)
    // @GetMapping("/approve/{orderId}")
    // public String approveOrderGet(@PathVariable Long orderId) {
    //     orderService.approveOrder(orderId);
    //     return "redirect:/admin/orders/pending?approved";
    // }
}
