package com.example.backend_sem2.dto.dtoForLogin;

import com.example.backend_sem2.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private GenderEnum gender;
    private String email;
    private LocalDate dob;
    private String verificationCode;
    private String avatarUrl;
}
