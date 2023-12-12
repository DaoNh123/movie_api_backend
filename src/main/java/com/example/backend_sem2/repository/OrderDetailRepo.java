package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT od.seat.id FROM OrderDetail od " +
            "JOIN FETCH Order d JOIN FETCH Slot s " +
            "WHERE s.id = :slotId")
    public List<String> getOrderedSeatNameByOrder_Slot_Id (Long slotId);
}
