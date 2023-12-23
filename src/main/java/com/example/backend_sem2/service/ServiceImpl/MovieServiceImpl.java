package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;

    @Override
    public Page<MovieResponseInPage> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel) {

        Page<Movie> moviePageableByCondition3 = movieRepo.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);

        return moviePageableByCondition3.map(movieMapper::toMovieResponseInPage);

    }


    @Override
    public MovieResponseWithComment getMovieWithCommentsById(Long id) {
        return movieMapper.toMovieResponseWithComment(movieRepo.getMovieWithComments(id));
    }

    /*  "getMoviePageableByCondition" already cover this method */
//    @Override
//    public List<MovieResponseInPage> findMoviesByMovieLabel(String movieLabel) {
//        MovieLabelEnum movieLabelEnum = getMovieLabelEnum(movieLabel);
//        if(movieLabelEnum == null) throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Your movie label is not exist!");
//        return movieRepo.findMoviesByMovieLabel(movieLabelEnum).stream()
//                .map(movieMapper::toMovieResponseInPage).toList();
//    }

    private MovieLabelEnum getMovieLabelEnum(String movieLabel) {
        try {
            return MovieLabelEnum.valueOf(movieLabel);
        } catch (IllegalArgumentException e) {
            return null;
            //throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Your movie label is not exist!");
        }
    }
}
