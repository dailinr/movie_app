package com.dailin.movie_app.persistence.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailin.movie_app.exception.ObjectNotFoundException;
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
    public List<Movie> findAll() {
        return movieCrudRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieCrudRepository.findByTitleContaining(title);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieCrudRepository.findByGenre(genre);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        return movieCrudRepository.findByGenreAndTitleContaining(genre, title);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Movie findOneById(Long id) {
        // findById devuelve un optional, pero con un orElseThrow devuelve el obj(movie)
        // si está presente y no sino devuelve una exception
        return movieCrudRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("[movie: "+ Long.toString(id)+"]"));
    }

    @Override
    public Movie createOne(Movie movie) {
        return movieCrudRepository.save(movie);
    }

    @Override
    public Movie updateOneById(Long id, Movie newMovie) {
        
        // usamos el mismo metodo service findOneById y enseguida se valida que si exista la pelicula
        Movie oldMovie = this.findOneById(id);

        oldMovie.setGenre(newMovie.getGenre());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setDirector(newMovie.getDirector());

        return movieCrudRepository.save(oldMovie);
    }

    @Override
    public void deleteOneById(Long id) {
        // usamos el mismo metodo service findOneById y enseguida se valida que si exista la pelicula
        Movie movie = this.findOneById(id);

        // metodo delete recibe como parametro la entidad como tal
        movieCrudRepository.delete(movie);
    }

}