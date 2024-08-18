package com.example.backend_sem2.dto.orderResponseInfoOverview;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderDetailInOrderResOverview {
    @JsonProperty("orderDetailId")
    private Long id;
    private String seatName;
    private Double price;
}
