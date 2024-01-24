package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.security.AuthorityRepo;
import com.example.backend_sem2.security.UserRepo;
import com.example.backend_sem2.security.entityForSecurity.User;
import com.example.backend_sem2.service.interfaceService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private AuthorityRepo authorityRepo;

    @Override
    public User addNewUser(User user) {
        if(userRepo.existsByUsername(user.getUsername())){
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Username have already exist! Please choose another one!");
        }
        user.addAuthority(authorityRepo.findByAuthorityName("USER"));

        return userRepo.save(user);
    }
}
