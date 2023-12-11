package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long> {
}
