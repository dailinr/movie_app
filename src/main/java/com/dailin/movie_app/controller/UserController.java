package com.dailin.movie_app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.exception.ObjectNotFoundException;
import com.dailin.movie_app.persistence.entity.User;
import com.dailin.movie_app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String name) {

        List<User> usuarios = null;

        if(StringUtils.hasText(name)) {
            usuarios = userService.findAllByName(name);
        }
        else{
            usuarios = userService.findAll();
        }

        return ResponseEntity.ok(usuarios);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity<User> findOneByUsername (@PathVariable String username) {

        try {
            return ResponseEntity.ok(userService.findOneByUsername(username));
        } catch (ObjectNotFoundException e) {
            // return ResponseEntity.status(404).build(); //
            return ResponseEntity.notFound().build(); 
        }
        
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createOne(@RequestBody User user, HttpServletRequest request) {
       
        User userCreated = userService.createOne(user);
        String baseUrl = request.getRequestURL().toString();
        // creamos la nueva localizacion paara acceder al documento recien creado
        URI newLocation = URI.create(baseUrl+"/"+userCreated.getId());


        return ResponseEntity
            .created(newLocation)
            .body(userCreated);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{username}")
    public ResponseEntity<User> updateOneByUsername(@PathVariable String username, @RequestBody User user){

        try {
            User userUpdated = userService.updatedOneByUsername(username, user);
            return ResponseEntity.ok(userUpdated);
        } 
        catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
        
        try {
            userService.deleteOneByUsername(username);
            return ResponseEntity.noContent().build();
        } 
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
