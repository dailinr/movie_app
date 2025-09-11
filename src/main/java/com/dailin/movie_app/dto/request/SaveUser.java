package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SaveUser(
    
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9-_]{8, 255}") 
    // puede incluir mayus y minus, numeros y (_ o -) . con minimo 8 caracteres
    String username,
    
    @Size(max = 255)
    String name,
    
    @NotBlank
    @Size(min = 10, max = 255)
    String password,
    
    @JsonProperty(value = "password_repeated") 
    @NotBlank
    @Size(min = 10, max = 255)
    String passwordRepeated

) implements Serializable { }
