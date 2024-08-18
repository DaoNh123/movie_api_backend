package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderRequestWithLoginAccount;
import com.example.backend_sem2.dto.orderResponseInfoOverview.OrderResponseOverview;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.OrderResponse;
import com.example.backend_sem2.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    String getEmailByOrderId(Long id);

    Order getOrderCustomById(Long id);
    OrderResponse createOrderWithLoginAccount(HttpServletRequest request, OrderRequestWithLoginAccount orderRequestWithLoginAccount);

    List<OrderResponseOverview> listingOrderByUser(HttpServletRequest request);
}
