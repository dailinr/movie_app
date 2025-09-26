package com.dailin.movie_app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dailin.movie_app.persistence.entity.Movie;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
     
    // buscar todas las peliculas que su titulo contenga la cadena del parametro
    // List<Movie> findByTitleContaining(String title);

    // buscar todas las peliculas que sean de determinado genero (palabra completa)
    // List<Movie> findByGenre(MovieGenre genre);

    // aplicando ambos filtros (titulo y genero)
    // List<Movie> findByGenreAndTitleContaining(MovieGenre genre, String title);
    
    // con un año minimo de lanzamiento (mayor o igual)
    // List<Movie> findByGenreAndTitleContainingAndRealeaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear);
    
}