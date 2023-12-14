package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Order extends BaseEntity{
    private String customerName;
    private String customerAddress;
    private Long customerAge;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    private Slot slot;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<OrderDetail> orderDetailList;

    protected Order(final OrderBuilder<?, ?> b) {
        super(b);
        this.customerName = b.customerName;
        this.customerAddress = b.customerAddress;
        this.customerAge = b.customerAge;
        this.slot = b.slot;
        this.orderDetailList = b.orderDetailList;

//        if(slot != null){
//            if(slot.getOrderList() == null){
//                slot.setOrderList(new ArrayList<>());
//            }
//            slot.getOrderList().add(this);
//        }
    }

    @PrePersist
    public void saveInChild (){
        if(this.orderDetailList != null){
            orderDetailList.stream()
                    .forEach(orderDetail -> orderDetail.setOrder(this));
        }
        if(this.slot != null){
            this.slot.addOrder(this);
        }
    }
}
