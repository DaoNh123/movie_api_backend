package com.example.backend_sem2.utils;

import com.example.backend_sem2.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Base64;

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

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Decode the Basic authentication header
            String base64Credentials = authHeader.substring(6);
//            String credentials = new String(Base64Utils.decodeFromString(base64Credentials));
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes);

            // Extract username from decoded credentials
            String[] parts = credentials.split(":");
            if (parts.length == 2) {
                username = parts[0];
            }
        }
        return username;
    }
}
