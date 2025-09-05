package com.dailin.movie_app.dto.response;

import java.io.Serializable;
import java.util.List;

import com.dailin.movie_app.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GetMovie(
    Long id, String title, String director, MovieGenre genre,
    @JsonProperty(value = "release_year") int releaseYear,
    List<GetRating> ratings
) implements Serializable {

    public static record GetRating(
        Long id, int rating, String username
    ) implements Serializable {}
}
