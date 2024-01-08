package com.example.backend_sem2.security;

import com.example.backend_sem2.security.entityForSecurity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
}
