package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderResponseInfo.OrderResponse;
import com.example.backend_sem2.entity.Order;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    Order getOrderById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    String getEmailByOrderId(Long id);

    Order getOrderCustomById(Long id);
}
