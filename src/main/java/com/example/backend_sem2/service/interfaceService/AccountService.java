package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.JwtResponse;
import com.example.backend_sem2.dto.LoginRequest;

public interface AccountService {
    String registerUser (CreateUserRequest createUserRequest);
    String verify(String email, String verificationCode);

    JwtResponse login(LoginRequest loginRequest);
}
