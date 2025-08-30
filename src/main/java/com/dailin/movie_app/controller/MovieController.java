package com.dailin.movie_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> findAll() {
        System.out.println("Entro al metodo findAll de movieController");
        return movieService.findAll();
    }
}
