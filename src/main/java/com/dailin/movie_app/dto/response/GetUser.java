package com.dailin.movie_app.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetUser(
    String username, String name, List<GetRating> ratings
) implements Serializable {
    
    public static record GetRating(
        long id, int rating,
        @JsonProperty(value = "movie_title") String movieTitle,
        @JsonProperty(value = "movie_id") long movieId 
    ) implements Serializable {}
}
