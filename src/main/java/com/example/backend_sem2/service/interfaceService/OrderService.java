package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> createOrder(OrderRequest orderRequest);
}
