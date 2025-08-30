package com.dailin.movie_app.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.util.MovieGenre;

public interface MovieCrudRepository extends JpaRepository<Movie, Long> {
     
    // buscar todas las peliculas que su titulo contenga la cadena del parametro
    List<Movie> findByTitleContaining(String title);

    // buscar todas las peliculas que sean de determinado genero (palabra completa)
    List<Movie> findByGenre(MovieGenre genre);

    // aplicando ambos filtros (titulo y genero)
    List<Movie> findByGenreAndTitleContaining(MovieGenre genre, String title);

    
}
