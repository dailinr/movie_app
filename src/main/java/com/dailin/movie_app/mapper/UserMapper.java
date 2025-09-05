package com.dailin.movie_app.mapper;

import java.util.List;

import com.dailin.movie_app.dto.request.SaveUser;
import com.dailin.movie_app.dto.response.GetUser;
import com.dailin.movie_app.persistence.entity.User;

public class UserMapper {

    // recibe la entidad y devuelve un getmovie
    public static GetUser toGetDto (User entity) {
        if(entity == null) return null;

        return new GetUser(
            entity.getUsername(), 
            entity.getName(), 
            null
        );
    }

    // cuando necesite mappear una list de entidades a un list DTO de respuesta
    public static List<GetUser> toGetDtoList(List<User> entities) {
        
        if(entities == null) return null;

        return entities.stream() // List<Movie> -> Stream<Movie>
            .map(UserMapper::toGetDto) // Stream<Movie>  -> Stream<GetMovie>
            // .map(each -> MovieMapper.toGetDto(each)) - forma larga
            .toList(); // finalmente se convierte otra vez a un List
    }

    // pasar de un saveUser (dto de peticion) a una entidad User
    public static User toEntity(SaveUser saveDto) {
        
        if(saveDto == null) return null;

        User newUser = new User();
        newUser.setUsername(saveDto.username());
        newUser.setName(saveDto.name());
        newUser.setPassword(saveDto.password());
        
        return newUser;
    } 
}