package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.dto.request.MovieSearchCriteria;
import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;

public interface MovieService {

    // listar todas las peliculas
    List<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria);    
    
    GetMovie findOneById(Long id);

    GetMovie createOne(SaveMovie movie);

    GetMovie updateOneById(Long id, SaveMovie movie);

    void deleteOneById(Long id);
}