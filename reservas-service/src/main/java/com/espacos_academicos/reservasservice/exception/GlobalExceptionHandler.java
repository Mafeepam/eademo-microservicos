package com.espacos_academicos.reservasservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String errorType, String message, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", errorType);
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<Object> buildValidationErrorResponse(HttpStatus status, String errorType, List<String> messages, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", errorType);
        body.put("messages", messages); // Note: 'messages' plural for list of validation errors
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler({RecursoNaoEncontradoException.class})
    public ResponseEntity<Object> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Recurso Não Encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler({ConflitoReservaException.class})
    public ResponseEntity<Object> handleConflitoReservaException(ConflitoReservaException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Conflito de Reserva", ex.getMessage(), request);
    }

    @ExceptionHandler({OperacaoNaoPermitidaException.class})
    public ResponseEntity<Object> handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Operação Não Permitida", ex.getMessage(), request);
    }

    @ExceptionHandler({IntegracaoServicoException.class})
    public ResponseEntity<Object> handleIntegracaoServicoException(IntegracaoServicoException ex, WebRequest request) {
        // Logar a causa raiz se disponível, pois é um erro de integração
        if (ex.getCause() != null) {
            // Use seu logger aqui: logger.error("Erro de integração detalhado:", ex.getCause());
            System.err.println("Erro de integração detalhado: " + ex.getCause().getMessage());
        }
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de Integração", ex.getMessage(), request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Argumento Inválido", ex.getMessage(), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return buildValidationErrorResponse(HttpStatus.BAD_REQUEST, "Erro de Validação", validationErrors, request);
    }

    // Handler genérico para qualquer outra exceção não tratada especificamente
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        // Use seu logger aqui: logger.error("Erro inesperado:", ex);
        System.err.println("Erro inesperado: " + ex.getMessage());
        ex.printStackTrace(); // Importante para debug em desenvolvimento
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Interno do Servidor", "Ocorreu um erro inesperado. Tente novamente mais tarde.", request);
    }
}