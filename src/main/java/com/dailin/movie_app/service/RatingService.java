package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.persistence.entity.Rating;

public interface RatingService {
    
    List<Rating> findAll();
    
    List<Rating> findAllByMovieId(Long movieId);
    
    List<Rating> findAllByUsername(String username);

    Rating findOneById(Long id);

    Rating createOne(Rating rating);

    Rating updateOneById(Long id, Rating rating);

    void deleteOneById(Long id);
}
