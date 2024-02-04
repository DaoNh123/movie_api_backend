package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.FindLatestSlotRequest;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.dto.createNewSlot.CreateSlotRequest;
import com.example.backend_sem2.entity.Slot;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface SlotService {
    List<Slot> getSlotsByMovie_Id(Long id);

    List<SlotResponse> getSlotsByMovie_IdBetweenTwoZonedDateTimes(Pageable pageable, Long id, ZonedDateTime startOfShowDate, ZonedDateTime endOfShowDate);

    SlotResponse addNewSlot(CreateSlotRequest createSlotRequest);

    SlotResponse getLatestSlotByTheaterRoomIdAndDate(FindLatestSlotRequest findLatestSlotRequest);
}
