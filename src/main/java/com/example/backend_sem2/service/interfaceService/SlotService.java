package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.entity.Slot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface SlotService {
    List<Slot> getSlotsByMovie_Id(Long id);


    List<Slot> getSlotsByMovie_IdAndShowDate(Pageable pageable, Long id, ZonedDateTime startOfShowDate, ZonedDateTime endOfShowDate);
}
