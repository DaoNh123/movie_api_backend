package com.example.backend_sem2.dto;

import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.exception.CustomErrorException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMovieRequest {
    @NotBlank
    private String movieName;
//    private Double imdbRatings;
    @JsonIgnore
    private String posterUrl;
//    private String director;
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    @NotBlank
    private ZonedDateTime openingTime;      // The time which customer have right to book ticket
    @NotBlank
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
    private String youtubeLink;              // this will save "youtubeLink" only
    @NotNull
    private List<String> categoryList;
    @NotBlank
    private MovieLabelEnum movieLabel;

    private MultipartFile poster;

    @AssertTrue(message = "Category List can't empty or contain any \"null\" value !")
    private boolean validateSeatIdList(){
        if(CollectionUtils.isEmpty(categoryList)) return false;
        else return !categoryList.contains(null);
    }

    public String getYoutubeId(){
        // Find the index of "=" and "&" in the URL
        if(this.youtubeLink == null) return null;

        int equalIndex = this.youtubeLink.indexOf('=');
        int ampersandIndex = this.youtubeLink.indexOf('&');

        if(equalIndex == -1) throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Youtube link have wrong format!");
        // Extract the substring between "=" and "&" or from "=" to the end if "&" is not present
        String resultString;
        if (ampersandIndex != -1) {
            resultString = this.youtubeLink.substring(equalIndex + 1, ampersandIndex);
        } else {
            resultString = this.youtubeLink.substring(equalIndex + 1);
        }

        return resultString;
    }


//    public List<Category> convertTo
}
