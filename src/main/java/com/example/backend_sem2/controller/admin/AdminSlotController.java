package com.example.backend_sem2.controller.admin;

import com.example.backend_sem2.controller.publicEndpoint.SlotController;
import com.example.backend_sem2.dto.FindLatestSlotRequest;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.dto.createNewSlot.CreateSlotRequest;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.service.interfaceService.SlotService;
import com.sun.tools.jconsole.JConsoleContext;
import com.sun.tools.jconsole.JConsolePlugin;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/admin/slots")
@AllArgsConstructor
public class AdminSlotController {
    private SlotService slotService;

    @PostMapping(value = {"", "/"})
    public SlotResponse createSlotForMovie(
//            @RequestParam(name = "movie-id") String movieId,
            @RequestBody CreateSlotRequest createSlotRequest
            ){
        return slotService.addNewSlot(createSlotRequest);
    }

    @PostMapping("/latest-time-slot")
    public SlotResponse getLatestSlotByTheaterRoomIdAndDate (
            @RequestBody FindLatestSlotRequest findLatestSlotRequest
    ){
        System.out.println("findLatestSlotRequest = " + findLatestSlotRequest);
        return slotService.getLatestSlotByTheaterRoomIdAndDate(findLatestSlotRequest);
    }
}
