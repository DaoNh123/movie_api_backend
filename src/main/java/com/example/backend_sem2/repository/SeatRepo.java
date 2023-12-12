package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s JOIN TheaterRoom tr JOIN Slot slot " +
            "WHERE slot.id = :slotId")
    List<Seat> getSeatBySlotId(Long slotId);
}
