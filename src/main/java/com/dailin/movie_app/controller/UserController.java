package com.dailin.movie_app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailin.movie_app.dto.request.SaveUser;
import com.dailin.movie_app.dto.response.GetUser;
import com.dailin.movie_app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<GetUser>> findAll(@RequestParam(required = false) String name) {

        List<GetUser> usuarios = null;

        if(StringUtils.hasText(name)) {
            usuarios = userService.findAllByName(name);
        }
        else{
            usuarios = userService.findAll();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUser> findOneByUsername (@PathVariable String username) {
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @PostMapping
    public ResponseEntity<GetUser> createOne(@RequestBody @Valid SaveUser saveDto, HttpServletRequest request) {
       
        GetUser userCreated = userService.createOne(saveDto);
        String baseUrl = request.getRequestURL().toString();
        // creamos la nueva localizacion paara acceder al documento recien creado
        URI newLocation = URI.create(baseUrl+"/"+saveDto.username());

        return ResponseEntity
            .created(newLocation)
            .body(userCreated);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{username}")
    public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username, @RequestBody SaveUser saveDto){

        GetUser userUpdated = userService.updatedOneByUsername(username, saveDto);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
        
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
