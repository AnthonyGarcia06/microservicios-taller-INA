package com.taller.ms_pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ApiError> handlePedidoNotFound(PedidoNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "not found",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CantidadExcedeLimiteException.class)
    public ResponseEntity<ApiError> handleCantidadExcedeLimite(CantidadExcedeLimiteException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "bad request",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LimitePedidosActivosException.class)
    public ResponseEntity<ApiError> handleLimitePedidosActivos(LimitePedidosActivosException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PedidoYaCanceladoException.class)
    public ResponseEntity<ApiError> handlePedidoYaCancelado(PedidoYaCanceladoException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Caso especial de ms-pedidos: un error que en realidad "viene de otro microservicio".
    // RestClient lanza esta excepcion cuando ms-usuarios o ms-productos responden con un
    // codigo de error (4xx o 5xx). En vez de convertirlo en un 500 generico, propagamos
    // el mismo codigo de estado que respondio el microservicio dependiente.
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ApiError> handleErrorDeOtroMicroservicio(RestClientResponseException ex) {
        ApiError error = new ApiError(
                ex.getStatusCode().value(),
                ex.getStatusText(),
                "Error al comunicarse con otro microservicio: " + ex.getResponseBodyAsString(),
                null
        );
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> listError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "bad request",
                "Uno o mas campos no estan validados",
                listError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleError(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal server error",
                "Ocurrio un error inesperado: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
