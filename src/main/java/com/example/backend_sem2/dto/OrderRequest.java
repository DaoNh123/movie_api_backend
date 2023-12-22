package com.example.backend_sem2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
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
//    @NonNull
    private String customerName;
    private String customerAddress;
    private Long customerAge;
    @Email
    private String customerEmail;
    private Long movieId;
    private Long slotId;
    private List<Long> seatIdList;

}
