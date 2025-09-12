package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.dailin.movie_app.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveMovie(
    
    @NotBlank(message = "Title can't be blank")
    @Size(min = 4, max = 255, message = "Title must have between {min} and {max} characters, current value = ${validatedValue}")
    String title, 

    @NotBlank(message = "Director can't be blank, current value = ${validatedValue}")
    @Size(min = 4, max = 255, message = "Director must have between {min} and {max} characters, current value = ${validatedValue}")
    String director, 

    MovieGenre genre,
    
    @Min(value = 1900, message = "release_year must have a minimun value of {value}, current value = ${validatedValue}")
    @Max(value = 2025, message = "release_year must have a maximun value of {value}, current value = ${validatedValue}")
    @JsonProperty(value = "release_year")
    int releaseYear

    // @JsonProperty("availability_end_time")
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // @Future // validacion solo para fechas futuras
    // @PastOrPresent // validacion solo para fechas pasadas o presente
    // LocalDate availabilityEndTime

) implements Serializable { }