package com.example.backend_sem2.controller.admin;

import com.example.backend_sem2.dto.TheaterRoomResponse;
import com.example.backend_sem2.service.interfaceService.TheaterRoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/theater-rooms")
@AllArgsConstructor
public class AdminTheaterRoomController {
    private TheaterRoomService theaterRoomService;

    @GetMapping(value = {"", "/"})
    public List<TheaterRoomResponse> getAllTheaterRooms()
    {
        return theaterRoomService.getAllTheaterRooms();
    }
}
