package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.BaseEntity;
import com.example.backend_sem2.enums.ActionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmailTemplate extends BaseEntity {
    private ActionTypeEnum actionType;
    private String htmlContent;
}
