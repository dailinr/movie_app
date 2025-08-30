package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.util.MovieGenre;

public interface MovieService {

    // listar todas las peliculas
    List<Movie> findAll();
    
    // filtrar peliculas por su titulo
    List<Movie> findAllByTitle(String title);
    
    List<Movie> findAllByGenre(MovieGenre genre);
    
    List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title);

    Movie findOneById(Long id);

    Movie createOne(Movie movie);

    Movie updateOneById(Long id, Movie movie);

    void deleteOneById(Long id);
}
