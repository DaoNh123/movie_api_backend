package com.example.backend_sem2.dto.orderResponseInfo_InDetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class SlotInOrderRes {
    @JsonProperty("slotId")
    private Long id;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private MovieInOrderRes movie;
    private String theaterRoom;
}
