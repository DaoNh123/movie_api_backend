package com.example.backend_sem2.dto.OrderResponseInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderDetailInOrderRes {
    private Long orderDetailId;
    private SeatResponseInOrderRes seat;
}
