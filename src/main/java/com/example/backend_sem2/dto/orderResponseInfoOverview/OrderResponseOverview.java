package com.example.backend_sem2.dto.orderResponseInfoOverview;

import com.example.backend_sem2.dto.orderResponseInfo_InDetail.SlotInOrderRes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderResponseOverview {
    @JsonProperty("orderId")
    private Long id;
    private SlotInOrderRes slot;                // done
    private List<OrderDetailInOrderResOverview> orderDetailList;

    @JsonProperty("totalPrice")
    public Double getTotalPrice (){
        if(!this.orderDetailList.isEmpty()){
            return this.orderDetailList.stream()
                    .map(OrderDetailInOrderResOverview::getPrice)
                    .reduce(0D, (subtotal, price) -> subtotal + price);
        }
        return 0D;
    }
}
