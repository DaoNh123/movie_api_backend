package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.repository.OrderRepo;
import com.example.backend_sem2.service.interfaceService.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        return orderService.createOrder(orderRequest);
    }
}
