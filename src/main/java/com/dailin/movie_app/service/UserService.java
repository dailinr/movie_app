package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.persistence.entity.User;

public interface UserService {

    List<User> findAll();

    List<User> findAllByName(String name);

    User findOneByUsername(String username);

    User updatedOneByUsername(String username, User user);

    User createOne(User user);

    void deleteOneByUsername(String username);
}
