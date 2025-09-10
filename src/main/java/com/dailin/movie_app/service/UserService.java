package com.dailin.movie_app.service;

import java.util.List;

import com.dailin.movie_app.dto.request.SaveUser;
import com.dailin.movie_app.dto.response.GetUser;

public interface UserService {

    List<GetUser> findAll();

    List<GetUser> findAllByName(String name);

    GetUser findOneByUsername(String username);

    GetUser updatedOneByUsername(String username, SaveUser saveDto);

    GetUser createOne(SaveUser saveDto);

    void deleteOneByUsername(String username);
}