package com.example.backend_sem2.controller.user;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderRequestWithLoginAccount;
import com.example.backend_sem2.dto.OrderResponseInfo.OrderResponse;
import com.example.backend_sem2.mapper.OrderMapper;
import com.example.backend_sem2.service.interfaceService.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/orders")
@AllArgsConstructor
public class UserOrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    @PostMapping("/")
    public OrderResponse createOrderWithLoginAccount (
            HttpServletRequest request,
            @RequestBody @Valid OrderRequestWithLoginAccount orderRequestWithLoginAccount
    ) {
        return orderService.createOrderWithLoginAccount(request, orderRequestWithLoginAccount);
    }
}
