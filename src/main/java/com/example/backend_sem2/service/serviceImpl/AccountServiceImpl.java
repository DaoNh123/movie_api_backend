package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.dtoForLogin.JwtResponse;
import com.example.backend_sem2.dto.dtoForLogin.LoginRequest;
import com.example.backend_sem2.dto.dtoForLogin.UserDto;
import com.example.backend_sem2.enums.ActionTypeEnum;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.UserMapper;
import com.example.backend_sem2.model.RabbitMQMessage;
import com.example.backend_sem2.rabbitMQProducer.MessageProducer;
import com.example.backend_sem2.repository.AuthorityRepo;
import com.example.backend_sem2.repository.UserRepo;
import com.example.backend_sem2.security.*;
import com.example.backend_sem2.entity.User;
import com.example.backend_sem2.service.interfaceService.AccountService;
import com.example.backend_sem2.service.interfaceService.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private AuthorityRepo authorityRepo;
    private MessageProducer messageProducer;
    private AmazonService amazonService;

    public String registerUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);

        saveUserAndSendVerificationEmail(user);
        return "Register successful!";
    }

    @Override
    public String registerUser2(MultipartFile avatar, CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);

        if(avatar != null){
            try {
                user.setAvatarUrl(amazonService.handleImageUploading("user_avatar", avatar));
            } catch (IOException e) {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "An error have occurred when uploading avatar!");
            }
        }

        saveUserAndSendVerificationEmail(user);
        return "Register successful!";
    }

    private void saveUserAndSendVerificationEmail(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Username has been existed");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Email has been existed");
        }
        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        user.setVerificationCode(createVerificationCode());
        user.setEnabled(false);
        user.addAuthority(authorityRepo.findByAuthorityName("USER"));

        RabbitMQMessage verifyMessage = RabbitMQMessage.builder()
                .actionType(ActionTypeEnum.USER_CREATED)
                .destinationEmail(user.getEmail())
                .data(userMapper.toUserDto(user))
                .build();
        messageProducer.sendUserCreatedMessage(verifyMessage);

        System.out.println(user);
        userRepo.save(user);
    }

    private String createVerificationCode() {
        return UUID.randomUUID().toString();
    }

    public String verify(String email, String verificationCode) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "User is not exist");
        } else if (user.isEnabled()) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "User already is active!");
        } else {
            boolean checkVerificationCode = user.getVerificationCode().equals(verificationCode);

            if (checkVerificationCode) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                userRepo.save(user);
                return "Verify successfully!";
            } else {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Wrong verificationCode");
            }
        }
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        System.out.println("__Login__");
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            System.out.println("authentication = " + authentication);

            if (authentication.isAuthenticated()) {
                boolean isAdmin = new MyUserDetails(userRepo.getUserByUsername(loginRequest.getUsername()))
                        .getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ADMIN"));

//                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                final String jwt = jwtService.createToken(
                        Map.ofEntries(
                                Map.entry("isAdmin", isAdmin)
                        )
                        ,loginRequest.getUsername()
                );
                System.out.println("jwtService.extractUsername(jwt) = " + jwtService.extractUsername(jwt));

                UserDto userDto = userMapper.toUserDto(userRepo.findByUsername(loginRequest.getUsername()));

                return new JwtResponse(jwt, userDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Authentication fails!");
    }
}
