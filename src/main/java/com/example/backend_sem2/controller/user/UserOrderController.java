package com.example.backend_sem2.controller.user;

import com.example.backend_sem2.dto.OrderRequestWithLoginAccount;
import com.example.backend_sem2.dto.orderResponseInfoOverview.OrderResponseOverview;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.OrderResponse;
import com.example.backend_sem2.service.interfaceService.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/orders")
@AllArgsConstructor
public class UserOrderController {
    private OrderService orderService;

    @PostMapping("/")
    public OrderResponse createOrderWithLoginAccount (
            HttpServletRequest request,
            @RequestBody @Valid OrderRequestWithLoginAccount orderRequestWithLoginAccount
    ) {
        return orderService.createOrderWithLoginAccount(request, orderRequestWithLoginAccount);
    }

    @GetMapping("/listing-order")
    public List<OrderResponseOverview> listingOrderByUser(
            HttpServletRequest request
    ){
        return orderService.listingOrderByUser(request);
    }
}
