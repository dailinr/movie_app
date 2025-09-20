package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SaveUser(
    
    @NotBlank(message = "{generic.notblank}")
    @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}")
    // puede incluir mayus y minus, numeros y (_ o -) . con minimo 8 caracteres
    String username,
    
    @Size(max = 255, message = "{generic.size}")
    String name,
    
    @NotBlank(message = "{generic.notblank}")
    @Size(min = 10, max = 255, message = "{generic.size}")
    String password,
    
    @JsonProperty(value = "password_repeated") 
    @NotBlank(message = "{generic.notblank}")
    @Size(min = 10, max = 255, message = "{generic.size}")
    String passwordRepeated

) implements Serializable { }
