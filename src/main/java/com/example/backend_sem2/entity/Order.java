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
public class Order extends BaseEntity {
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_age")
    private Long customerAge;
    @Column(name = "customer_email")
    private String customerEmail;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JsonIgnore
    private Slot slot;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    @JsonIgnore
    private List<OrderDetail> orderDetailList;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private User user;

    protected Order(final OrderBuilder<?, ?> b) {
        super(b);
        this.customerName = b.customerName;
        this.customerAge = b.customerAge;
        this.slot = b.slot;
        this.orderDetailList = b.orderDetailList;
        this.customerEmail = b.customerEmail;
        this.user = b.user;

        if (this.slot != null) {
            this.slot.addOrder(this);
        }
    }

    @PrePersist
    @PreUpdate
    public void saveInChild() {
        if (this.orderDetailList != null) {
            orderDetailList.stream()
                    .forEach(orderDetail -> orderDetail.setOrder(this));
        }

    }

    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", customerAge=" + customerAge +
                '}';
    }
}
