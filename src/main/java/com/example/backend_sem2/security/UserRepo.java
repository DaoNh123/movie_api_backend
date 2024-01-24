package com.example.backend_sem2.security;

import com.example.backend_sem2.security.entityForSecurity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.authoritySet WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
