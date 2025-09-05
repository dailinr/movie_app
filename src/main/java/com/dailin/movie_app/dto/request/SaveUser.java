package com.dailin.movie_app.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SaveUser(
    String username,
    String name,
    String password,
    @JsonProperty(value = "password_repeated") String passwordRepeated
) implements Serializable {

}
