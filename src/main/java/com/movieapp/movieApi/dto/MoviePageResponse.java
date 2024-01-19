package com.movieapp.movieApi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movies , int pageNumber , int pageSize , int totalNumOfPages, boolean isLast) {
}
