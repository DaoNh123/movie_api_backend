package com.example.backend_sem2.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    private Long id;
    private String subject;
    private String body;
}
