package com.dailin.movie_app.persistence.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.dailin.movie_app.dto.request.MovieSearchCriteria;
import com.dailin.movie_app.persistence.entity.Movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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

        if(searchCriteria.genre() != null) {
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), this.searchCriteria.genre());
            // m.genre = ?
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

        // necesitamos convertir la lista de predicados a un arreglo

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        // select m.* from movie 1 = 1 and m.title like '%1%' and m.genre = ?2 and m.releaseYear >= ?3
    }
}