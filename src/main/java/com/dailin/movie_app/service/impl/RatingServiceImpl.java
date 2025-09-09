package com.dailin.movie_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.persistence.entity.Rating;
import com.dailin.movie_app.persistence.repository.RatingCrudRepository;
import com.dailin.movie_app.service.RatingService;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Rating> findAll() {
        return ratingCrudRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Rating> findAllByMovieId(Long movieId) {
        return ratingCrudRepository.findByMovieId(movieId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Rating> findAllByUsername(String username) {
        return ratingCrudRepository.findByUsername(username);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Rating findOneById(Long id) {
        return ratingCrudRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("[rating: "+ Long.toString(id)+ "]"));
    }

    @Override
    public Rating createOne(Rating rating) {
        return ratingCrudRepository.save(rating);
    }

    @Override
    public Rating updateOneById(Long id, Rating newRating) {
        Rating oldRating = this.findOneById(id); // este metodo ya maneja la excepcion

        // recordemos que estos serán los dos atributos que se utilizaran para actualizar
        oldRating.setUserId(newRating.getUserId());
        oldRating.setMovieId(newRating.getMovieId());

        return ratingCrudRepository.save(oldRating);
    }

    @Override
    public void deleteOneById(Long id) {
        // primera alternativa
        // Rating rating = this.findOneById(id); // este metodo ya maneja la excepcion
        // ratingCrudRepository.delete(rating);

        // segunda alternativa. menos costosa
        if(ratingCrudRepository.existsById(id)) {
            ratingCrudRepository.deleteById(id);
            return;
        }
        
        throw new ObjectNotFoundException("[rating: "+ Long.toString(id)+ "]");
    }


}
