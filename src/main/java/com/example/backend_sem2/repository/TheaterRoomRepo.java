package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.TheaterRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRoomRepo extends JpaRepository<TheaterRoom, Long> {

}
