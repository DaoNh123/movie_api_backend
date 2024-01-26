package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CreateUserRequest;
import com.example.backend_sem2.dto.JwtResponse;
import com.example.backend_sem2.dto.LoginRequest;
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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String registerUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);

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

//        sendVerificationEmail(user.getEmail(), user.getVerificationCode());
        return "Register successful!";
    }

    private String createVerificationCode() {
        return UUID.randomUUID().toString();
    }

    /*  *** ==>> Using RabbitMQ to solve send Email problem ***     */
//    private void sendVerificationEmail (String email, String verificationCode)
//    {
//        String subject = "Active your account at WebBanSach";
//
//        String verifyLink = "http://localhost:3000/verify/" + email + "/" + verificationCode;
//        String text = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <" + email + ">:<br/><h1>"
//                + verificationCode + "</h1>";
//        text += "<a href=\"[[verifyLink]]\">Verify your account</a>";
//        text = text.replace("[[verifyLink]]", verifyLink);
//
//        System.out.println("***");
//        System.out.println(text);
//        String fromAddress = System.getenv("EMAIL");
//        emailService.sendMessage(fromAddress, email, subject, text);
//    }

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
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                System.out.println("jwtService.extractUsername(jwt) = " + jwtService.extractUsername(jwt));

                return new JwtResponse(jwt);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Authentication fails!");
    }
}
