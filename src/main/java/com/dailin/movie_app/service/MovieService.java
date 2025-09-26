package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;
import com.dailin.movie_app.util.MovieGenre;

public interface MovieService {

    // listar todas las peliculas
    List<GetMovie> findAll(String title, MovieGenre genre, Integer minReleaseYear );    
    // filtrar peliculas por su titulo
    // List<GetMovie> findAllByTitle(String title);   
    // List<GetMovie> findAllByGenre(MovieGenre genre);
    // List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);
    // List<GetMovie> findAllByGenreAndTitleAndReleaseYear(MovieGenre genre, String title, Integer minReleaseYear);

    GetMovie findOneById(Long id);

    GetMovie createOne(SaveMovie movie);

    GetMovie updateOneById(Long id, SaveMovie movie);

    void deleteOneById(Long id);
}