package com.example.backend_sem2.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
//    @NotBlank
    private String posterUrl;
    private String director;
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    private ZonedDateTime openingTime;      // The time which customer have right to book ticket
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
    private String iframe;              // this will will save "youtubeLink" only
    private List<String> categoryList;
    @NotBlank
    private String movieLabel;

    @AssertTrue(message = "Category List can't empty or contain any \"null\" value !")
    private boolean validateSeatIdList(){
        if(CollectionUtils.isEmpty(categoryList)) return false;
        else return !categoryList.contains(null);
    }
}
