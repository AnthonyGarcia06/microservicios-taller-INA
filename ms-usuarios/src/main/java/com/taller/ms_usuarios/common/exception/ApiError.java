package com.taller.ms_usuarios.common.exception;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> detalles;


    public ApiError(int status, String error, String message, List<String> detalles) {
        this.timestamp=LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.detalles = detalles;
    }
}
