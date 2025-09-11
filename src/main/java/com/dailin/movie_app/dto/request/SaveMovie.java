package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.dailin.movie_app.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveMovie(
    
    @NotBlank
    @Size(min = 4, max = 255)
    String title, 

    @NotBlank
    @Size(min = 4, max = 255)
    String director, 

    MovieGenre genre,
    
    @Min(value = 1900)
    @Max(value = 2025)
    @JsonProperty(value = "release_year")
    int releaseYear

) implements Serializable { }
