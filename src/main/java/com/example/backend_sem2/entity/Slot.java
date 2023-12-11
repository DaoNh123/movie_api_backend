package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "slots")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Slot extends BaseEntity{
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    private Movie movie;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    private TheaterRoom theaterRoom;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "slot",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    private List<Order> orderList;
}
