package com.espacos_academicos.espacosservice.exception; // Certifique-se que este é o seu pacote de exceções

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Esta anotação faz o Spring retornar 404 Not Found automaticamente se esta exceção não for pega por um @ExceptionHandler
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}