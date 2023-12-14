package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
//    @Query("SELECT od.seat.seatName FROM OrderDetail od " +
//            "JOIN FETCH od.order d JOIN FETCH d.slot s " +
//            "WHERE s.id = :slotId")

//@Query("SELECT se.seatName FROM OrderDetail AS od " +
//        "INNER JOIN FETCH od.order AS o INNER JOIN FETCH o.slot AS s " +
//        "INNER JOIN od.seat AS se " +
//        "WHERE s.id = :slotId")
//    List<String> getOrderedSeatNameByOrder_Slot_Id (Long slotId);

    public List<OrderDetail> getOrderDetailByOrder_Slot_Id(Long slotId);
}
