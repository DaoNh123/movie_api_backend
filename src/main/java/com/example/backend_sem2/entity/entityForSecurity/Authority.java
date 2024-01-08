package com.example.backend_sem2.entity.entityForSecurity;

import com.example.backend_sem2.entity.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "authorities")
public class Authority extends BaseEntity {

}
