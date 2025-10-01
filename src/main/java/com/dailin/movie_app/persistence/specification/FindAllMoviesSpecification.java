package com.dailin.movie_app.persistence.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.dailin.movie_app.dto.request.MovieSearchCriteria;
import com.dailin.movie_app.persistence.entity.Movie;
import com.dailin.movie_app.persistence.entity.Rating;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

// <> en base a que entidad se armará la query (from tabl)
public class FindAllMoviesSpecification implements Specification<Movie> {

    private MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // root = from Movie m
        // query = crirerios de la consulta en si misma
        // criteriaBuilder = predicados (true or false) para los where

        // esta será la lista de predicados
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.searchCriteria.title())){
            // nombre atributo de la entidad y el valor de la busqueda (viene desde el controlador)
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%"+this.searchCriteria.title()+"%");
            // m.title like '%sjdjdj%'

            predicates.add(titleLike);
        }

        if(this.searchCriteria.genres() != null && this.searchCriteria.genres().length > 0) {
            // debemos convertir el listado de generos, de un arreglo a un List<>
            Predicate genreEqual = criteriaBuilder.in(root.get("genre")).value(Arrays.stream(this.searchCriteria.genres()).toList()); 
            // and m.genre in (?, ?, ?)
            predicates.add(genreEqual);
        }

        if(searchCriteria.minReleaseYear() != null && this.searchCriteria.minReleaseYear().intValue() > 0){
            
            Predicate releaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(
                root.get("releaseYear"), this.searchCriteria.minReleaseYear()
            );

            // m.releaseYear >= ?
            predicates.add(releaseYearGreaterThanEqual);
        }

        if(searchCriteria.maxReleaseYear() != null && this.searchCriteria.maxReleaseYear().intValue() > 0) {

            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(
                root.get("releaseYear"), this.searchCriteria.maxReleaseYear()
            );

            // m.releaseYear <= ?
            predicates.add(releaseYearLessThanEqual);
        }

        if(this.searchCriteria.minAverageRating() != null && this.searchCriteria.minAverageRating() > 0) {
            // (SELECT avg(r1_0.rating) FROM movie_app.rating r1_0 WHERE r1_0.movie_id = 1) >= ?5

            // creamos la subconsulta en una funcion aparte
            Subquery<Double> averageRatingSubquery = getAveragRatingSubquery(root, query, criteriaBuilder);

            // creamos el predicado comparando si el promedio(subquery) >= que el parametro del cliente(double)
            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }
        
        // necesitamos convertir la lista de predicados a un arreglo
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


        // select m.* 
        // from movie m 
        // where 1 = 1 and m.title like '%1%' 
        //             and m.genre in (?, ? , ?)
        //             and m.releaseYear >= ?3
        //             and m.realseYear <= ?4
        //             and (SELECT avg(r1_0.rating) 
        //                 FROM movie_app.rating r1_0
        //                 WHERE r1_0.movie_id = root.get(?1)) >= searchCriteria.minAverageRating()
    }

    private static Subquery<Double> getAveragRatingSubquery(Root<Movie> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        // Esta subconsulta nos dará un listado de los promedios de los rating de cada pelicula
        
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class); // el valor que devuelve la subquery es un double
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class); // from de entidad Rating

        // dado que estamos en una MovieSpecificacion el root por defecto es el de movie
        averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating"))); // select avg(r1_0.rating)

        // where movie.id = rating.movie_id
        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
        averageRatingSubquery.where(movieIdEqual);

        return averageRatingSubquery;
    }
}