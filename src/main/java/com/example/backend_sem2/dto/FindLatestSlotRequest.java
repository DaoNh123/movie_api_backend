package com.example.backend_sem2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FindLatestSlotRequest {
    private Long theaterRoomId;
    private LocalDate findingDate;
}
