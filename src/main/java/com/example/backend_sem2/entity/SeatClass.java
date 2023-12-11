package com.example.backend_sem2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "slots")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SeatClass extends BaseEntity{
    @Column(name = "seat_class_name")
    private String seatClassName;
    private Double price;
}
