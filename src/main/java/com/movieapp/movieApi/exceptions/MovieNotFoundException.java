package com.movieapp.movieApi.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException() {
        super("Could not found movie by requested ID");
    }
}
