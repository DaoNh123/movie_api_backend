package com.example.backend_sem2.controller.publicEndpoint;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderResponseInfo.OrderResponse;
import com.example.backend_sem2.mapper.OrderMapper;
import com.example.backend_sem2.service.interfaceService.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    /*  This endpoint have used "OrderResponse" as a DTO    */
    @PostMapping("/")
    public OrderResponse createOrderWithoutLogin (
            @RequestBody @Valid OrderRequest orderRequest
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
}
