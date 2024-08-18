package com.example.backend_sem2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "slots")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(callSuper = true)
public class Slot extends BaseEntity{
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JsonIgnore
    private Movie movie;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JsonIgnore
    private TheaterRoom theaterRoom;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "slot",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JsonIgnore
    private List<Order> orderList;

    @PrePersist
    public void setEndTime(){
        if(this.movie != null && this.movie.getDuration() != null){
            this.endTime = this.startTime.plusMinutes(this.movie.getDuration());
        }
    }
    public void addOrder (Order order){
        if(orderList == null){
            orderList = new ArrayList<>();
        }
        orderList.add(order);
    }

    protected Slot(final SlotBuilder<?, ?> b) {
        super(b);
        this.startTime = b.startTime;
        this.endTime = b.endTime;
        this.movie = b.movie;
        this.theaterRoom = b.theaterRoom;
        this.orderList = b.orderList;
        if(movie != null){
            if(movie.getSlotList() == null){
                movie.setSlotList(new ArrayList<>());
            }
            movie.getSlotList().add(this);
        }
    }

    @Override
    public String toString() {
        return "Slot{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
