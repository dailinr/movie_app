package com.dailin.movie_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.persistence.entity.User;
import com.dailin.movie_app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll(@RequestParam(required = false) String name) {

        List<User> usuarios = null;

        if(StringUtils.hasText(name)) {
            usuarios = userService.findAllByName(name);
        }
        else{
            usuarios = userService.findAll();
        }

        return usuarios;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public User findOneByUsername (@PathVariable String username) {
        return userService.findOneByUsername(username);
    }
}
