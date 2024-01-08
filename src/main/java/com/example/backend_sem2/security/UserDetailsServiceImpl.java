package com.example.backend_sem2.security;

import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.security.entityForSecurity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        if(user == null){
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Could not find user!");
        }
        return new MyUserDetails(user);
    }
}
