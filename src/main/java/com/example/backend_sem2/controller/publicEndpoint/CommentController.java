package com.example.backend_sem2.controller.publicEndpoint;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.security.JwtService;
import com.example.backend_sem2.service.interfaceService.CommentService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;
    private JwtService jwtService;

//    @PostMapping({"", "/"})
//    public CommentResponse saveComment(
//            HttpServletRequest request,
//           @Valid @RequestBody CommentRequest commentRequest
//    ){
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//        // "Bearer" is used to marked what is the token, it can be customized.
//        if(authHeader != null && authHeader.startsWith("Bearer ")){
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//        }
//        System.out.println("inner POST method");
//        System.out.println("username = " + username);
//
//
//        return commentService.saveComment(commentRequest);
//    }


//    @PutMapping("/update/{id}")
//    public CommentResponse updateComment(
//            @Valid @RequestBody CommentRequest commentRequest,
//            @PathVariable Long id
//    ){
//        return commentService.updateComment(commentRequest, id);
//    }
}
