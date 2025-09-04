package com.dailin.movie_app.persistence.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.persistence.entity.User;
import com.dailin.movie_app.persistence.repository.UserCrudRepository;
import com.dailin.movie_app.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public User createOne(User user) {
        return userCrudRepository.save(user);
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
    public List<User> findAll() {
        return userCrudRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<User> findAllByName(String name) {
        return userCrudRepository.findByNameContaining(name);
    }
    
    @Transactional(readOnly = true)
    @Override
    public User findOneByUsername(String username) {
        return userCrudRepository.findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("[user: " + username + " ]")) ;
    }

    @Override
    public User updatedOneByUsername(String username, User user) {
        User oldUser = this.findOneByUsername(username);

        oldUser.setName(user.getName());
        // --- validar el password ---
        oldUser.setPassword(user.getPassword());

        return userCrudRepository.save(oldUser);
    }

}
