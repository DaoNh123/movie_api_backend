package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Slot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long> {

    @Query("FROM Slot s JOIN s.movie m WHERE m.id = :id AND " +
            "((cast(:startOfDay AS DATE) IS NULL) OR s.startTime >= :startOfDay) AND " +
            "((cast(:endOfDay AS DATE) IS NULL) OR s.startTime <= :endOfDay)")
    List<Slot> getSlotsByMovie_IdAndStartTimeBetween(Pageable pageable, @Param("id") Long id, @Param("startOfDay") ZonedDateTime startOfDay, @Param("endOfDay") ZonedDateTime endOfDay);

    @Query("FROM Slot s JOIN FETCH s.movie m WHERE m.id = :movieId")
    List<Slot> getSlotsByMovie_Id(Long movieId);

    @Query("SELECT s FROM Slot s LEFT JOIN s.theaterRoom tr " +
            "WHERE tr.id = :theaterRoomId " +
            "AND (cast(:startOfDay AS DATE) IS NULL OR s.startTime BETWEEN :startOfDay AND :endOfDay) " +
            "ORDER BY s.endTime DESC ")
    Slot findSlotWithGreatestEndTime(Long theaterRoomId, @Param("startOfDay") ZonedDateTime startOfDay, @Param("endOfDay") ZonedDateTime endOfDay);

    List<Slot> findFirst1ByTheaterRoom_IdAndEndTimeBetween(Long theaterRoomId, @Param("startOfDay") ZonedDateTime startOfDay, @Param("endOfDay") ZonedDateTime endOfDay);
}
