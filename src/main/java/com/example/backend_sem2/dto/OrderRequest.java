package com.example.backend_sem2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank(message = "Customer name can't be null")
    private String customerName;
    @NotBlank(message = "Customer address can't be null")
    private String customerAddress;
    @NotNull(message = "Customer age can't be null")
    private Long customerAge;
    @Email(message = "Customer Email doesn't have right format!")   // "@Email" doesn't check "null"
    @NotNull(message = "Customer email can't be null")
    private String customerEmail;
//    @NotNull(message = "Movie Id can't be null")
//    private Long movieId;
    @NotNull(message = "Slot Id can't be null")
    private Long slotId;
    @NotEmpty(message = "List of seatId can't be empty")
    private List<Long> seatIdList;

    @AssertTrue(message = "Seat Id List can't empty or contain any \"null\" value !")
    private boolean validateSeatIdList(){
        if(CollectionUtils.isEmpty(seatIdList)) return false;
        else return !seatIdList.contains(null);
    }
}
