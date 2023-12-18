package com.example.backend_sem2.dto.OrderResponseInfo;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.TheaterRoom;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class SlotInOrderRes {
    private Long slotId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private MovieInOrderRes movie;
    private String theaterRoom;
}
