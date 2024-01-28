package com.example.backend_sem2.dto.dtoForLogin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private final String jwt;
    private UserDto userDto;
}
