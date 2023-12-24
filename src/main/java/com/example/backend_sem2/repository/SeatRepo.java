package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s JOIN FETCH s.theaterRoom tr JOIN FETCH tr.slotList slot " +
            "JOIN FETCH s.seatClass se WHERE " +
            " slot.id = :slotId")
    List<Seat> getSeatBySlotId(Long slotId);

//    @Query("SELECT se.seatName FROM OrderDetail AS od " +
//        "INNER JOIN FETCH od.order AS o INNER JOIN FETCH o.slot AS s " +
//        "INNER JOIN od.seat AS se " +
//        "WHERE s.id = :slotId")
//    List<String> getOrderedSeatNameByOrder_Slot_Id (Long slotId);
//    boolean isSeatBelongToSlot(String seatId, slotId)
}
