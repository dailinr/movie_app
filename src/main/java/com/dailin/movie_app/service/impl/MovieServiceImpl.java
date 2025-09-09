package com.dailin.movie_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;
import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.mapper.MovieMapper;
import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.persistence.repository.MovieCrudRepository;
import com.dailin.movie_app.service.MovieService;
import com.dailin.movie_app.util.MovieGenre;



@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    @Autowired 
    private MovieCrudRepository movieCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAll() {
        // return movieCrudRepository.findAll();
        List<Movie> entities = movieCrudRepository.findAll();
        return MovieMapper.toGetDtoList(entities);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByTitle(String title) {
        // return movieCrudRepository.findByTitleContaining(title);
        List<Movie> entities = movieCrudRepository.findByTitleContaining(title);
        return MovieMapper.toGetDtoList(entities);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByGenre(MovieGenre genre) {
        // return movieCrudRepository.findByGenre(genre);
        List<Movie> entities = movieCrudRepository.findByGenre(genre);
        return MovieMapper.toGetDtoList(entities);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        // return movieCrudRepository.findByGenreAndTitleContaining(genre, title);
        List<Movie> entities = movieCrudRepository.findByGenreAndTitleContaining(genre, title);
        return MovieMapper.toGetDtoList(entities);
    }
    
    @Transactional(readOnly = true)
    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(this.findOneEntityById(id)); // sino existe lanza una excepcion, devuelve la entidad    
    }

    @Transactional(readOnly = true)
    public Movie findOneEntityById(Long id) {
        // findById devuelve un optional, pero con un orElseThrow devuelve el obj(movie)
        // si está presente y no sino devuelve una exception
        return movieCrudRepository.findById(id)  // devuelve un optional<movie>
            .orElseThrow(() -> new ObjectNotFoundException("[movie: "+ Long.toString(id)+"]"));
    }

    @Override
    public GetMovie createOne(SaveMovie movie) {
        Movie newMovie = MovieMapper.toEntity(movie); // de saveMovie a una entidad
        return MovieMapper.toGetDto( // devuelve un getDto (response)
            movieCrudRepository.save(newMovie)
        );
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie newMovie) {
        
        // usamos el mismo metodo service findOneById y enseguida se valida que si exista la pelicula
        Movie oldMovie = this.findOneEntityById(id);
        
        // mappear la entidad con los varlores nuevos
        MovieMapper.updateEntity(oldMovie, newMovie);

        return MovieMapper.toGetDto(
            movieCrudRepository.save(oldMovie)
        );
    }

    @Override
    public void deleteOneById(Long id) {
        // usamos el mismo metodo service findOneById y enseguida se valida que si exista la pelicula
        Movie movie = this.findOneEntityById(id);

        // metodo delete recibe como parametro la entidad como tal
        movieCrudRepository.delete(movie);
    }

}