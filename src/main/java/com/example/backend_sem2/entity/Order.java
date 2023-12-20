package com.example.backend_sem2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity{
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "customer_age")
    private Long customerAge;
    @Column(name = "customer_email")
    private String customerEmail;
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JsonIgnore
    private Slot slot;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    @JsonIgnore
    private List<OrderDetail> orderDetailList;

    protected Order(final OrderBuilder<?, ?> b) {
        super(b);
        this.customerName = b.customerName;
        this.customerAddress = b.customerAddress;
        this.customerAge = b.customerAge;
        this.slot = b.slot;
        this.orderDetailList = b.orderDetailList;

        if(this.slot != null){
            this.slot.addOrder(this);
        }
    }

    @PrePersist
    public void saveInChild (){
        if(this.orderDetailList != null){
            orderDetailList.stream()
                    .forEach(orderDetail -> orderDetail.setOrder(this));
        }
//        if(this.slot != null){
//            this.slot.addOrder(this);
//        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerAge=" + customerAge +
                ", orderDetailList=" + orderDetailList +
                '}';
    }
}
