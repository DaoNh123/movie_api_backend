package com.example.backend_sem2.dto;

import com.example.backend_sem2.Enum.SeatStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatResponse {
    private Long seatId;
    private String seatName;
    @JsonIgnore
    private SeatClassResponse seatClassResponse;
    private SeatStatusEnum status;

    public SeatClassResponse getSeatClass(){
        return this.seatClassResponse;
    }
}
