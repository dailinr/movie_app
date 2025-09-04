package com.dailin.movie_app.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.dailin.movie_app.persistence.entity.User;

public interface UserCrudRepository extends JpaRepository<User, Long> {

    // filtrar (todos) los usuarios por su nombre
    List<User> findByNameContaining(String name);

    // encontrar un usuario en especifico por medio del username
    Optional<User> findByUsername(String username);

    @Modifying // especificamos que este metodo no será una consulta
    @Transactional  // ya que es un query definido por mi, debo indicar que es transactional si no es de consulta
    int deleteByUsername(String username);
}