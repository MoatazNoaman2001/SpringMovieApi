package com.movieapp.movieApi.server;

import com.movieapp.movieApi.dto.MovieDto;
import com.movieapp.movieApi.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieServices {

    MovieDto SaveMovie(MovieDto movieDto , MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movie_id);

    MovieDto updateMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    void deleteMovie(Integer integer);

    MoviePageResponse getAllMovieWithPaging(int pageNumber, int pageSize);
    List<MovieDto> getAllMovie();
}
