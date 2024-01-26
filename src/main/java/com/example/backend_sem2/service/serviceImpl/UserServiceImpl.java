package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.UserMapper;
import com.example.backend_sem2.repository.AuthorityRepo;
import com.example.backend_sem2.security.MyUserDetails;
import com.example.backend_sem2.repository.UserRepo;
import com.example.backend_sem2.entity.User;
import com.example.backend_sem2.service.interfaceService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private AuthorityRepo authorityRepo;
    private UserMapper userMapper;

//    @Override
//    public CreateUserRequest addNewUser(User user) {
//        if(userRepo.existsByUsername(user.getUsername())){
//            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Username have already exist! Please choose another one!");
//        }
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.addAuthority(authorityRepo.findByAuthorityName("USER"));
//
//        CreateUserRequest dto = userMapper.toDto(userRepo.save(user));
//        return dto;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        System.out.println("userRepo.existsByUsername(username) = " + userRepo.existsByUsername(username));
        if(user == null){
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Could not find user!");
        }
        return new MyUserDetails(user);
    }
}
