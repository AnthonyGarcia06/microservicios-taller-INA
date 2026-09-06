package com.taller.ms_productos.exception;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(Long id) {
        super("No se encontró producto con id: " + id);
    }

}