package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long> {
    public List<Slot> getSlotsByMovie_Id (Long id);
}
