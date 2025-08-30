package com.dailin.movie_app.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dailin.movie_app.persistence.entity.Rating;

public interface RatingCrudRepository extends JpaRepository<Rating, Long> {

    // lista de calificaciones de una pelicula
    List<Rating> findByMovieId(Long id);
    
    // buscar los ratings de un usuario a partir de su username
    // opcion 1: 
    List<Rating> findByUserUsername(String username);

    // opcion 2: 
    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = ?1")
    List<Rating> findByUsername(String username);
}