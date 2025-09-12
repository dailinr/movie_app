package com.dailin.movie_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.dailin.movie_app.dto.request.SaveUser;
import com.dailin.movie_app.dto.response.GetUser;
import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.mapper.UserMapper;
import com.dailin.movie_app.persistence.entity.User;
import com.dailin.movie_app.persistence.repository.UserCrudRepository;
import com.dailin.movie_app.service.UserService;
import com.dailin.movie_app.service.validator.PasswordValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public GetUser createOne(SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated()); // y si no lanza la excepcion InvalidPasswordException
        User newUser = UserMapper.toEntity(saveDto);
        return UserMapper.toGetDto(userCrudRepository.save(newUser));
    }
    
    @Override
    public void deleteOneByUsername(String username) {
        // User user = this.findOneByUsername(username);
        // userCrudRepository.delete(user);
        
        if(userCrudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user: "+ username+ "]");
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<GetUser> findAll() {
        List<User> entities = userCrudRepository.findAll();
        return UserMapper.toGetDtoList(entities);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<GetUser> findAllByName(String name) {
        List<User> entities = userCrudRepository.findByNameContaining(name);
        return UserMapper.toGetDtoList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public GetUser findOneByUsername(String username) {
        return UserMapper.toGetDto(
            this.findOneEntityByUsername(username)
        );
    }
    
    @Transactional(readOnly = true)
    private User findOneEntityByUsername(String username) {
        return userCrudRepository.findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("[user: " + username + " ]")) ;
    }

    @Override
    public GetUser updatedOneByUsername(String username, SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());
        User oldUser = this.findOneEntityByUsername(username);

        UserMapper.updateEntity(oldUser, saveDto);

        return UserMapper.toGetDto(userCrudRepository.save(oldUser));
    }
}