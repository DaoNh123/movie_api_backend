package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "slots")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OrderDetail extends BaseEntity{
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.MERGE, CascadeType.PERSIST
            }
    )
    private Seat seat;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.MERGE, CascadeType.PERSIST
            }
    )
    private Order order;
}
