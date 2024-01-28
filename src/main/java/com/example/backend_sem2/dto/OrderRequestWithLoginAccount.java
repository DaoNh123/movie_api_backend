package com.example.backend_sem2.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.CollectionUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestWithLoginAccount {
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
