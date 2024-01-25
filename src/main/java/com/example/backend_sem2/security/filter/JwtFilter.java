package com.example.backend_sem2.security.filter;

import com.example.backend_sem2.security.JwtService;
import com.example.backend_sem2.service.interfaceService.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
//    @Autowired
    private JwtService jwtService;
//    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*  Get "token" and "username" from "request" */
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        // "Bearer" is used to marked what is the token, it can be customized.
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        /*  Create new "token" from "username" and "userDetail" from token which is received in "request" */
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() != null){
        /*  Something's wrong with "SecurityContextHolder.getContext().getAuthentication() != null" ==> Remove  */
        if(username != null){

            UserDetails userDetails = userService.loadUserByUsername(username);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
