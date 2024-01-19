package com.movieapp.movieApi.repo;

import com.movieapp.movieApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User , Integer> {

    Optional<User> findByEmail(String Email);
}
