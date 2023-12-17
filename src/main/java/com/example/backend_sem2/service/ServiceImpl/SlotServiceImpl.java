package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.repository.SlotRepo;
import com.example.backend_sem2.service.interfaceService.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {
    private SlotRepo slotRepo;

    @Override
    public List<Slot> getSlotsByMovie_Id(Long id) {
        return slotRepo.getSlotsByMovie_Id(id);
    }

    @Override
    public List<Slot> getSlotsByMovie_IdAndShowDate(Pageable pageable, Long id, ZonedDateTime startOfShowDate, ZonedDateTime endOfShowDate) {
        return slotRepo.getSlotsByMovie_IdAndStartTimeBetween(pageable, id, startOfShowDate, endOfShowDate);
    }
}