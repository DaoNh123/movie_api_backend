package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "slots")
@AllArgsConstructor
@NoArgsConstructor
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
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    private List<Seat> seatList;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<OrderDetail> orderDetail;
}
