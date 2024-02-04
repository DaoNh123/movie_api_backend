package com.example.backend_sem2.dto.dtoForMovie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseOverview {
    private Long id;
    private String movieName;
    private ZonedDateTime openingTime;      // make sure startTime of Slot must be after "openingTime"
    private ZonedDateTime closingTime;
}
