package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class TheaterRoom extends BaseEntity{
    private String theaterRoomName;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "theaterRoom", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    private List<Slot> slotList;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "theaterRoom", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    private List<Seat> seatList;
}
