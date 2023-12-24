package com.example.backend_sem2.model.rapidApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotSummary {
    private String author;      // can convert to "director"
    private String id;
    private String text;        // can convert to "description" for first choose
}
