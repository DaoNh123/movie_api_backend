package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(String authorityName);
}
