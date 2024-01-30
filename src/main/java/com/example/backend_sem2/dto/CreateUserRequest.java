package com.example.backend_sem2.dto;

import com.example.backend_sem2.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    @NotBlank
    @Length(min = 5, message = "Username must be at least 5 characters")
    private String username;
    @NotBlank
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private GenderEnum gender;
    @NotNull(message = "User email can't be null")
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;      // new
}
