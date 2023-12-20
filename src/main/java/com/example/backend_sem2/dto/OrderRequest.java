package com.example.backend_sem2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String customerName;
    private String customerAddress;
    private Long customerAge;
    private Long movieId;
    private Long slotId;
//    private String seatIdInString;
    private List<Long> seatIdList;
//    @JsonIgnore
//    private List<Long> seatIdList;
//
//    public void convertToSeatIdList(){
//        if(seatIdInString != null) this.seatIdList = Arrays.stream(seatIdInString.split(","))
//                .map(Long::parseLong).toList();
//    }

//    public List<Long> getSeatIdList(){
//        List<Long> longList = new ArrayList<>();
//        if(seatIdInString != null) longList = Arrays.stream(seatIdInString.split(","))
//                .map(String::trim)
//                .map(Long::parseLong).toList();
//        return longList;
//    }
}
