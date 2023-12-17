package com.example.backend_sem2.dto.OrderResponseInfo;

import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.entity.Slot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderResponse {
    private Long id;
    private String customerName;
    private String customerAddress;
    private Long customerAge;
    private String customerEmail;
    private SlotInOrderRes slot;
    private List<OrderDetailInOrderRes> orderDetailList;
}
