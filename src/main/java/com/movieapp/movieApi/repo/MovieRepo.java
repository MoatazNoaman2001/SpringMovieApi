package com.movieapp.movieApi.repo;


import com.movieapp.movieApi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MovieRepo extends JpaRepository<Movie, Integer> {
}
