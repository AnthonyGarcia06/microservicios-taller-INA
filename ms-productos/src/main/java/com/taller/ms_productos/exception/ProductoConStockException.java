package com.taller.ms_productos.exception;

public class ProductoConStockException extends RuntimeException {

    public ProductoConStockException(Integer stockActual) {
        super("No se puede eliminar el producto: todavía tiene stock disponible (" + stockActual + " unidades)");
    }

}