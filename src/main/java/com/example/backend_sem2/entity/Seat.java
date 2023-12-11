package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Seat extends BaseEntity{
    private String seatName;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private TheaterRoom theaterRoom;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private Order order;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private SeatClass seatClass;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<OrderDetail> orderDetail;
}
