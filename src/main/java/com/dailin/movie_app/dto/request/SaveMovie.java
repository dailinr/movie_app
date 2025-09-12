package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.dailin.movie_app.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveMovie(
    
    @NotBlank(message = "{generic.notblank}")
    @Size(min = 4, max = 255, message = "{generic.size}")
    String title, 

    @NotBlank(message = "{generic.notblank}")
    @Size(min = 4, max = 255, message = "{generic.size}")
    String director, 

    MovieGenre genre,
    
    @Min(value = 1900, message = "{generic.min}")
    @Max(value = 2025, message = "{generic.max}") 
    @JsonProperty(value = "release_year")
    int releaseYear

    // @JsonProperty("availability_end_time")
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // @Future // validacion solo para fechas futuras
    // @PastOrPresent // validacion solo para fechas pasadas o presente
    // LocalDate availabilityEndTime

) implements Serializable { }