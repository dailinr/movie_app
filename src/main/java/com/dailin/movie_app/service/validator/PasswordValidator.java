package com.dailin.movie_app.service.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.dailin.movie_app.exception.InvalidPasswordException;

public class PasswordValidator {

    public static void validatePassword(String password, String passwordRepeated) {

        // en caso de que alguno de los args esté vacio
        if (!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST.value(),
                        "Password must contain data", 
                        new IllegalArgumentException("Password must contain data"));
        }

        // validar que coincidan los password
        if(!password.equals(passwordRepeated)) {
            throw new InvalidPasswordException(password, passwordRepeated, "Password don't match.");
        }
    
        // en este punto ya se ha vaidado que las contraseñas sea iguales
        if(!containsNumber(password)) {
            throw new InvalidPasswordException(password, "Password must contain at least one number.");
        }

        // si contiene mayusculas
        if(!containsUpperCase(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one uppercase letter.");
        }

        // si contiene minusculas
        if(!containsLowerCase(password)){
            throw new InvalidPasswordException(password, "Password must contain at least one lowercase letter.");
        }

        if(!containsSpecialCharacter(password)) {
           throw new InvalidPasswordException(password, "Password must contain at least one special character."); 
        }
    }

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }

}