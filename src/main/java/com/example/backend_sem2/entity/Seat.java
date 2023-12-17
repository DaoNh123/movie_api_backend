package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

//@Data
@Getter@Setter
@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
//@ToString(callSuper = true)
public class Seat extends BaseEntity{
    @Column(name = "seat_name")
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
    private SeatClass seatClass;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "seat",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<OrderDetail> orderDetail;

    protected Seat(final SeatBuilder<?, ?> b) {
        super(b);
        this.seatName = b.seatName;
        this.theaterRoom = b.theaterRoom;
        this.seatClass = b.seatClass;
        this.orderDetail = b.orderDetail;
        if(theaterRoom != null){
            if(theaterRoom.getSeatList() == null){
                theaterRoom.setSeatList(new ArrayList<>());
            }
            theaterRoom.getSeatList().add(this);
        }
    }
}
