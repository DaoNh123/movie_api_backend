package com.example.backend_sem2.security;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.security.entityForSecurity.User;
import com.example.backend_sem2.service.interfaceService.AccountService;
import com.example.backend_sem2.service.interfaceService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    @PostMapping("/register")
    public String registerUser(@Validated @RequestBody CreateUserRequest createUserRequest){
        return accountService.registerUser(createUserRequest);
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
