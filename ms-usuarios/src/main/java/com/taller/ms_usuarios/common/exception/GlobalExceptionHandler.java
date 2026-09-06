package com.taller.ms_usuarios.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ApiError> handleEmailDuplicado(EmailDuplicadoException ex){

        ApiError error= new ApiError(

                HttpStatus.CONFLICT.value(),
                "conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){

        List<String> listError
                = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiError error= new ApiError(

                HttpStatus.BAD_REQUEST.value(),
                "bad request",
                "Uno o mas campos no estan validaos",
                listError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    };

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleError(Exception ex){

        ApiError error= new ApiError(

                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal server error",
                "Ocurrio un error inesperado: "+ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    };


    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ApiError> handleUsuarioNotFound (UsuarioNotFoundException ex){

        ApiError error= new ApiError(

                HttpStatus.NOT_FOUND.value(),
                "NOT FOUND",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}//endclass
