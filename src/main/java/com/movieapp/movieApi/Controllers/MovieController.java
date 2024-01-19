package com.movieapp.movieApi.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.movieApi.commmons.Constants;
import com.movieapp.movieApi.dto.MovieDto;
import com.movieapp.movieApi.dto.MoviePageResponse;
import com.movieapp.movieApi.model.Movie;
import com.movieapp.movieApi.repo.MovieRepo;
import com.movieapp.movieApi.server.MovieServicesImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@Log
@RequestMapping("/api/v1/")
@ComponentScan("com.movieapp.movieApi.server")
@Configuration
public class MovieController {

    MovieServicesImpl movieServices;

    public MovieController(MovieServicesImpl movieServices) {
        this.movieServices = movieServices;
    }

    @PostMapping("/addMovie")
    public void addMovie(@RequestBody Movie movie) {
        log.info("person added: " + movie.toString());
//        movieRepo.save(movie);
    }

    @PostMapping("/add")
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestPart String MovieDto) throws IOException {
        log.info(MovieDto);
        MovieDto movieDto = convertToMovieDto(MovieDto);
        if (movieDto == null)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        movieDto = movieServices.SaveMovie(movieDto, file);
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<MovieDto> updateMovie(@RequestPart(required = false) MultipartFile file, @RequestPart String MovieDto) throws IOException {
        MovieDto movieDto = convertToMovieDto(MovieDto);
        if (movieDto == null)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        log.info(movieDto.toString());
        movieDto = movieServices.updateMovie(movieDto, file);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException {
        movieServices.deleteMovie(id);
        return new ResponseEntity<>("deleted successfully with id : " + id, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Integer id) {
        System.out.println(" id : " + id);
        MovieDto movieDto = movieServices.getMovie(id);
        if (movieDto.getTitle() != null)
            return new ResponseEntity<>(movieDto, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(movieDto, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MovieDto>> getAllMovie() {
        return new ResponseEntity<>(movieServices.getAllMovie() , HttpStatus.FOUND);
    }

    @GetMapping("/Movies")
    public ResponseEntity<MoviePageResponse> getAllMoviePages(@RequestParam(defaultValue = Constants.PAGE_NUMBER) int page , @RequestParam(defaultValue = Constants.PAGE_SIZE) int size){
        return ResponseEntity.ok(movieServices.getAllMovieWithPaging(page , size));
    }



    private MovieDto convertToMovieDto(String movieDto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MovieDto movieDto1 =  mapper.readValue(movieDto, MovieDto.class);
            log.info(movieDto1.toString());
            return movieDto1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
