package com.dailin.movie_app.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.dailin.movie_app.util.MovieGenre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String director;
    
    @Enumerated(EnumType.STRING) // para que guarde el nombre y no su pos
    private MovieGenre genre;

    private int releaseYear;
    
    @CreationTimestamp // se auto-genera cuando ingresamos una nueva pelicula
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()") // no se puede modificar
    private LocalDateTime createdAt;

    // una pelicula puede tener muchas calificaciones
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private List<Rating> ratings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}