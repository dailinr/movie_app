package com.dailin.movie_app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;
import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.service.MovieService;
import com.dailin.movie_app.util.MovieGenre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false) String title, 
        @RequestParam(required = false) MovieGenre genre) 
    {

        List<GetMovie> peliculas = null;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMovie> findOneById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findOneById(id)); // sino logra encontrar la pelicula se lanza la excepcion
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(
        @Valid @RequestBody SaveMovie saveDto, 
        HttpServletRequest request
    ) {
        
        // System.out.println("fecha: "+saveDto.availabilityEndTime());
        GetMovie movieCreated = movieService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity
            .created(newLocation)
            .body(movieCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GetMovie> updateOneById(@PathVariable Long id, 
        @RequestBody @Valid SaveMovie saveDto
    ) {
       
        try {
            GetMovie updatedMovie = movieService.updateOneById(id, saveDto);
            return ResponseEntity.ok(updatedMovie);
        } 
        catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        
        try {
            movieService.deleteOneById(id);
            return ResponseEntity.noContent().build(); // noContent por ser de tipo void
        } 
        catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    } 
}