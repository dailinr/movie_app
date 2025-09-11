package com.dailin.movie_app.persistence.entity;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rating {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // pelicula que se califica
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    
    // usuario que la califica
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // puede haber muchas calificaciones para una misma pelicula
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false) // la columna que permite la relacion/union, solo será de lectura
    private Movie movie;
    
    // puede haber muchas calificaciones para una misma pelicula
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // la columna que permite la relacion/union, solo será de lectura
    private User user;

    @Column(nullable = false)
    @Check(constraints = "rating >= 0 and rating <= 5")
    private int rating;
    
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
