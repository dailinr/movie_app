package com.dailin.movie_app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.dto.request.MovieSearchCriteria;
import com.dailin.movie_app.dto.request.SaveMovie;
import com.dailin.movie_app.dto.response.GetMovie;
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
        @RequestParam(required = false) MovieGenre[] genres,
        @RequestParam(required = false) Integer minReleaseYear, @RequestParam(required = false) Integer maxReleaseYear,
        @RequestParam(required = false) Integer minAverageRating ) 
    {
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genres, minReleaseYear, maxReleaseYear, minAverageRating);
        List<GetMovie> peliculas = movieService.findAll(searchCriteria);

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
       
        GetMovie updatedMovie = movieService.updateOneById(id, saveDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build(); // noContent por ser de tipo void
    } 
}