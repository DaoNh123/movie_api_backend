package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.FindLatestSlotRequest;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.dto.createNewSlot.CreateSlotRequest;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.SlotMapper;
import com.example.backend_sem2.repository.SlotRepo;
import com.example.backend_sem2.service.interfaceService.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {
    private SlotRepo slotRepo;
    private SlotMapper slotMapper;

    @Override
    public List<Slot> getSlotsByMovie_Id(Long id) {
        return slotRepo.getSlotsByMovie_Id(id);
    }

    @Override
    public List<SlotResponse> getSlotsByMovie_IdBetweenTwoZonedDateTimes(Pageable pageable, Long id, ZonedDateTime startOfShowDate, ZonedDateTime endOfShowDate) {
        return slotRepo.getSlotsByMovie_IdAndStartTimeBetween(pageable, id, startOfShowDate, endOfShowDate)
                .stream().map(slotMapper::toSlotResponse).toList();
    }

    @Override
    public SlotResponse addNewSlot(CreateSlotRequest createSlotRequest) {
        Slot newSlot = slotMapper.toEntity(createSlotRequest);
        slotRepo.save(newSlot);

        return slotMapper.toSlotResponse(newSlot);
    }

    @Override
    public SlotResponse getLatestSlotByTheaterRoomIdAndDate(FindLatestSlotRequest findLatestSlotRequest) {
        System.out.println("findLatestSlotRequest.getTheaterRoomId() = " + findLatestSlotRequest.getTheaterRoomId());
        System.out.println("findLatestSlotRequest.getFindingDate() = " + findLatestSlotRequest.getFindingDate());
        if(findLatestSlotRequest.getFindingDate() == null) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "FindLatestSlotRequest have wrong format!");
        }

        ZoneId zoneId = ZoneId.of("UTC+7");
        assert findLatestSlotRequest != null;

        ZonedDateTime startOfFindingDate = findLatestSlotRequest.getFindingDate().atStartOfDay(zoneId);
        ZonedDateTime endOfFindingDate = startOfFindingDate.plusDays(1L);

        List<Slot> findingSlot = slotRepo.findFirst1ByTheaterRoom_IdAndEndTimeBetween(
                findLatestSlotRequest.getTheaterRoomId(),
                startOfFindingDate,
                endOfFindingDate
        );

        /*  If findingSlot does not contain any Slot, this method with return a
        SlotResponse with "startTime" is 7 a.m ( the time which staff start a work day)
        and after 30 minutes, customer can start watch a first slot of Movie in our theater */

        if(findingSlot.isEmpty()) return SlotResponse.builder()
                .startTime(findLatestSlotRequest.getFindingDate().atTime(7, 0).atZone(zoneId))
                .build();

        return slotMapper.toSlotResponse(findingSlot.get(0));
    }
}
