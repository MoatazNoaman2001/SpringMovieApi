package com.movieapp.movieApi.model;


import com.movieapp.movieApi.dto.MovieDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "please provide movie title")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "please provide movie director")
    private String director;
    @Column(nullable = false)
    @NotBlank(message = "please provide movie studio")
    private String studio;
    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;
    @Column(nullable = false)
    @NotNull(message = "please provide movie releaseYear")
    private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message = "please provide movie poster")
    private String poster;

    @NotBlank(message = "please provide movie poster url")
    private String poster_url;

    public MovieDto toMovieDto(){
        return new MovieDto(
                id, title , director ,studio , movieCast , releaseYear, poster, poster_url
        );
    }
}
