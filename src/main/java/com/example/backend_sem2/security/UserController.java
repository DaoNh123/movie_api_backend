package com.example.backend_sem2.security;

import com.example.backend_sem2.security.entityForSecurity.User;
import com.example.backend_sem2.service.interfaceService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping({"", "/"})
    public User register(
            User user
    ){
        return userService.addNewUser(user);
    }
}
