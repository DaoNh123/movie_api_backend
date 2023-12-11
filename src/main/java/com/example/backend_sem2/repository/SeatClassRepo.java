package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.SeatClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatClassRepo extends JpaRepository<SeatClass, Long> {

}
