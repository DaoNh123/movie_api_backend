package com.example.backend_sem2.model.kinoCheckApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrailerContainer {
    @JsonProperty("trailer")
    private Trailer trailer;

    public String getYoutubeVideoId(){
        if(this.trailer != null) return trailer.getYoutubeVideoId();
        return null;
    }
}
