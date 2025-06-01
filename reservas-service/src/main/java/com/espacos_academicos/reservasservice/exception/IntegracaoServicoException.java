package com.espacos_academicos.reservasservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntegracaoServicoException extends RuntimeException {
    public IntegracaoServicoException(String message) { super(message); }
    public IntegracaoServicoException(String message, Throwable cause) { super(message, cause); }
}