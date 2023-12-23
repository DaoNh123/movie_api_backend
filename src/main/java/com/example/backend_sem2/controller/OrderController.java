package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderResponseInfo.OrderResponse;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.mapper.OrderMapper;
import com.example.backend_sem2.service.interfaceService.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    /*  This endpoint have used "OrderResponse" as a DTO    */
    @PostMapping("/")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        return orderService.createOrder(orderRequest);
    }

    /*  Get information for Order Detail Page  */
    @GetMapping("/{id}")
    public OrderResponse getOrderInfoById(
            @PathVariable Long id
    ) {
        return orderMapper.toDto(orderService.getOrderCustomById(id));
    }

//    @GetMapping("/withoutDto/{id}")
//    public Order getOrderInfoWithoutDtoById(
//            @PathVariable Long id
//    ) {
//        return orderService.getOrderCustomById(id);
//    }

    @GetMapping("/email/{id}")
    public ResponseEntity<?> getEmailByOrderId(
            @PathVariable Long id
    ) {
        String emailByOrderId = orderService.getEmailByOrderId(id);

        if(emailByOrderId == null) emailByOrderId = "";
        return ResponseEntity.ok(Map.of("email", emailByOrderId));
    }
}
