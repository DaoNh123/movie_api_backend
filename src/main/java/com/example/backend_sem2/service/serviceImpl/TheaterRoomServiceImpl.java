package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.TheaterRoomResponse;
import com.example.backend_sem2.entity.TheaterRoom;
import com.example.backend_sem2.mapper.TheaterRoomMapper;
import com.example.backend_sem2.repository.TheaterRoomRepo;
import com.example.backend_sem2.service.interfaceService.TheaterRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TheaterRoomServiceImpl implements TheaterRoomService {
    private TheaterRoomRepo theaterRoomRepo;
    private TheaterRoomMapper theaterRoomMapper;

    @Override
    public List<TheaterRoomResponse> getAllTheaterRooms() {
        List<TheaterRoom> theaterRoomList = theaterRoomRepo.findAll();
        return theaterRoomList.stream().map(theaterRoomMapper::toDto).toList();
    }
}
