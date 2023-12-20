package com.example.backend_sem2.dto.DtoForMovie;

import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.Enum.MovieLabelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseWithComment {
    private Long id;
    private String movieName;
    private String posterUrl;
    private String director;
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    private ZonedDateTime openingTime;      // The time which customer can book a ticket
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
    private String iframe;
    private List<String> categoryNameList;      // need custom expression
    private MovieLabelEnum movieLabel;
    private List<CommentResponse> commentList;

    public Double getAverageStar(){
        if(commentList.isEmpty()) return 0D;
        double averageStar = commentList.stream()
                .map(CommentResponse::getStarRate)
                .map(Long::doubleValue)
                .reduce(0D, Double::sum) / commentList.size();
        return  (double)Math.round(averageStar * 10) / 10;
    }
}
