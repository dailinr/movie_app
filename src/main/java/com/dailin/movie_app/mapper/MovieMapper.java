package com.dailin.movie_app.mapper;

import java.util.List;

import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;
import com.dailin.movie_app.persistence.entity.Movie;

public class MovieMapper {

    // recibe la entidad y devuelve un getmovie
    public static GetMovie toGetDto(Movie entity) {

        if(entity == null)  return null;

        return new GetMovie(
            entity.getId(), 
            entity.getTitle(), 
            entity.getDirector(), 
            entity.getGenre(), 
            entity.getReleaseYear(), 
            null
        );
    }

    // cuando necesite mappear una list de entidades a un list DTO de respuesta
    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        
        if(entities == null) return null;

        return entities.stream() // List<Movie> -> Stream<Movie>
            .map(MovieMapper::toGetDto) // Stream<Movie>  -> Stream<GetMovie>
            // .map(each -> MovieMapper.toGetDto(each)) - forma larga
            .toList(); // finalmente se convierte otra vez a un List
    }

    // pasar de un saveMovie a una entidad Movie
    public static Movie toEntity(SaveMovie saveDto) {
        
        if(saveDto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(saveDto.title());
        newMovie.setDirector(saveDto.director());
        newMovie.setReleaseYear(saveDto.releaseYear());
        newMovie.setGenre(saveDto.genre());
        
        return newMovie;
    }

    // metodo para actualizar 
    public static void updateEntity(Movie oldMovie, SaveMovie saveDto) {

        if(oldMovie == null || saveDto == null) return;

        oldMovie.setGenre(saveDto.genre());
        oldMovie.setReleaseYear(saveDto.releaseYear());
        oldMovie.setTitle(saveDto.title());
        oldMovie.setDirector(saveDto.director());
    } 
}
