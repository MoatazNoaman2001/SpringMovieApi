package com.movieapp.movieApi.dto;

import com.movieapp.movieApi.model.Movie;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
public class MovieDto {
    public MovieDto() {
    }

    private Integer id;

    @NotBlank(message = "please provide movie title")
    private String title;
    @NotBlank(message = "please provide movie director")
    private String director;
    @NotBlank(message = "please provide movie studio")
    private String studio;

    private Set<String> movieCast;
    @NotNull
    private Integer releaseYear;
    @NotBlank(message = "please provide movie poster")
    private String poster;
    @NotBlank(message = "please provide movie poster url")
    private String poster_url;

    public Movie toMovie(){
        return new Movie(
                id, title , director ,studio , movieCast , releaseYear, poster, poster_url
        );
    }
}
