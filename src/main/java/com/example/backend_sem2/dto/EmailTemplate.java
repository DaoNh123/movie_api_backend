package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.ActionTypeEnum;
import com.example.backend_sem2.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "email_teamplates")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmailTemplate extends BaseEntity {
    private ActionTypeEnum actionType;
    private String htmlContent;
}
