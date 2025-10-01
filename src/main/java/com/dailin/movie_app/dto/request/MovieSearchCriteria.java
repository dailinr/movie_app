package com.dailin.movie_app.dto.request;

import com.dailin.movie_app.util.MovieGenre;

// criterios de busqueda para las peliculas
public record MovieSearchCriteria(
    String title, 
    MovieGenre[] genres,
    Integer minReleaseYear,
    Integer maxReleaseYear,
    Integer minAverageRating
) { }