package com.movieapp.movieApi.server;

import com.movieapp.movieApi.dto.MovieDto;
import com.movieapp.movieApi.dto.MoviePageResponse;
import com.movieapp.movieApi.exceptions.MovieNotFoundException;
import com.movieapp.movieApi.model.Movie;
import com.movieapp.movieApi.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Configuration
public class MovieServicesImpl implements MovieServices {

    FileServices fileServices;
    MovieRepo movieRepo;

    public MovieServicesImpl(FileServices fileServices, MovieRepo movieRepo) {
        this.fileServices = fileServices;
        this.movieRepo = movieRepo;
    }

    @Value("${project.poster}")
    String path;

    @Value("${base.url}")
    String url;

    @Override
    public MovieDto SaveMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //save movie file
        String filename = fileServices.uploadFile(path, file);

        //set poster parameter
        movieDto.setPoster(filename);

        //map dto to object
        Movie movie = movieDto.toMovie();
        movie.setId(null);

        //save movie to db
        movieRepo.save(movie);

        //generate movie url
        movie.setPoster_url(url + "/file/" + movie.getPoster());

        //map object to dto
        return movie.toMovieDto();
    }

    @Override
    public MovieDto getMovie(Integer movie_id) {
        Movie movie =  movieRepo.findById(movie_id).orElseThrow(MovieNotFoundException::new);
        movie.setPoster_url(url + "file/" + movie.getPoster());
        return movie.toMovieDto();
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        if (Files.exists(Path.of(path + File.separator + file.getOriginalFilename()))){
            Files.delete(Path.of(path + File.separator + movieDto.getPoster()));
            Files.copy(file.getInputStream() , Path.of(path + File.separator + file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            movieDto.setPoster(path + File.separator + file.getOriginalFilename());
        }

        Movie movie = movieRepo.save(movieDto.toMovie());

        return movie.toMovieDto();
    }

    @Override
    public void deleteMovie(Integer integer) {
        movieRepo.deleteById(integer);
    }

    @Override
    public MoviePageResponse getAllMovieWithPaging(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber , pageSize);
        Page<Movie> moviePage = movieRepo.findAll(pageable);
        return new MoviePageResponse(moviePage.map(Movie::toMovieDto).stream().collect(Collectors.toList())
         , pageNumber , pageSize , moviePage.getTotalPages() , moviePage.isLast());
    }

    @Override
    public List<MovieDto> getAllMovie() {
        return movieRepo.findAll().stream().map(it->{
            it.setPoster_url(url + "file/" + it.getPoster());
            return it.toMovieDto();
        }).collect(Collectors.toList());
    }
}
