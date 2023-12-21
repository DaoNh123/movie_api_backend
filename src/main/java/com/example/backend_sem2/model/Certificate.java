package com.example.backend_sem2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate {
    private String certificate;         // convert to movie label
    private String certificateNumber;
    private String ratingReason;
    private String ratingsBody;
    private String country;
}
