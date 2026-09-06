package com.taller.ms_productos.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(Integer stockDisponible, Integer cantidadSolicitada) {
        super("Stock insuficiente: disponible " + stockDisponible + ", solicitado " + cantidadSolicitada);
    }

}