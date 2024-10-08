package com.example.backend_sem2.model;

import com.example.backend_sem2.enums.ActionTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitMQMessage {
    private ActionTypeEnum actionType;          // ORDER_CREATED, UPCOMING_MOVIE
    private Object data;
    private String destinationEmail;
}
