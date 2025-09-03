package com.dailin.movie_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.service.MovieService;
import com.dailin.movie_app.util.MovieGenre;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> findAll(@RequestParam(required = false) String title, 
        @RequestParam(required = false) MovieGenre genre) 
    {

        List<Movie> peliculas = null;

        // validamos que haya un valor para variable title y que no este vacio con espacione en blanco
        if(StringUtils.hasText(title) && genre != null) {
            peliculas = movieService.findAllByGenreAndTitle(genre, title);
        }
        else if(StringUtils.hasText(title)) {
            peliculas = movieService.findAllByTitle(title);
        }
        else if(genre != null){
            peliculas = movieService.findAllByGenre(genre);
        }
        else {
            peliculas = movieService.findAll();
        }

        // HttpHeaders headers = new HttpHeaders();
        // return new ResponseEntity<List<Movie>>(peliculas, headers, HttpStatus.OK); // opcion 1
        // return ResponseEntity.status(HttpStatus.OK).body(peliculas); // opcion 2
        return ResponseEntity.ok(peliculas); // opcion 3: mas usuada

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Movie> findOneById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(movieService.findOneById(id)); // sino logra encontrar la pelicula se lanza la excepcion
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        
    }
}
