package com.example.backend_sem2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheaterRoomResponse {
    private Long id;
    private String theaterRoomName;
}
