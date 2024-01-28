package com.example.backend_sem2.utils;

import com.example.backend_sem2.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorityUtility {
    private JwtService jwtService;
    public String extractUsernameFromRequest (HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        // "Bearer" is used to marked what is the token, it can be customized.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        return username;
    }
}
