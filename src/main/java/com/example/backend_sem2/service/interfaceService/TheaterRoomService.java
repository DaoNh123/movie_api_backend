package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.TheaterRoomResponse;
import com.example.backend_sem2.entity.TheaterRoom;

import java.util.List;

public interface TheaterRoomService {
    List<TheaterRoomResponse> getAllTheaterRooms();
}
