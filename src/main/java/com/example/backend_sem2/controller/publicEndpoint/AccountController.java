package com.example.backend_sem2.controller.publicEndpoint;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.dtoForLogin.JwtResponse;
import com.example.backend_sem2.dto.dtoForLogin.LoginRequest;
import com.example.backend_sem2.service.interfaceService.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/register")
    public String registerUser(@Validated @RequestBody CreateUserRequest createUserRequest){
        return accountService.registerUser(createUserRequest);
    }

    @PostMapping("/register2")
    public String registerUser2(
            @RequestPart("avatar") MultipartFile avatar,
            @RequestPart("createMovieRequest") @Validated CreateUserRequest createUserRequest
    ){
        return accountService.registerUser2(avatar, createUserRequest);
    }

    @GetMapping("/verify")
    public String verify (@RequestParam String email, @RequestParam String verificationCode){
        String response = accountService.verify(email, verificationCode);
        return response;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest)
    {
        return accountService.login(loginRequest);
    }
}
