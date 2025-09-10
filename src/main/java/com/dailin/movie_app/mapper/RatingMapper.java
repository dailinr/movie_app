package com.dailin.movie_app.mapper;

import java.util.List;

import com.dailin.movie_app.dto.response.GetMovie;
import com.dailin.movie_app.dto.response.GetUser;
import com.dailin.movie_app.persistence.entity.Rating;

public class RatingMapper {

    public static  GetMovie.GetRating toGetMovieRatingDto(Rating entity) {

        if(entity == null) return null;

        String username = entity.getUser() != null
            ? entity.getUser().getUsername()
            : null;

        return new GetMovie.GetRating(
            entity.getId(),
            entity.getRating(),
            username
        );
    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
        if(entity == null) return null;

        String movieTitle = entity.getMovie() != null
            ? entity.getMovie().getTitle()
            : null;

        return new GetUser.GetRating(
            entity.getId(),
            entity.getRating(), 
            movieTitle, 
            entity.getMovieId()
        );
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if(entities == null) return null;

        return entities.stream()
            .map(RatingMapper::toGetMovieRatingDto)
            .toList();
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if(entities == null) return null;

        return entities.stream()
            .map(RatingMapper::toGetUserRatingDto)
            .toList();
    }
}
