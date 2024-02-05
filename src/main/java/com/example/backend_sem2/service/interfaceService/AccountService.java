package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.dtoForLogin.JwtResponse;
import com.example.backend_sem2.dto.dtoForLogin.LoginRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

public interface AccountService {
    String registerUser (CreateUserRequest createUserRequest);
    String verify(String email, String verificationCode);

    JwtResponse login(LoginRequest loginRequest);

    String registerUser2(MultipartFile avatar, CreateUserRequest createUserRequest);
}
