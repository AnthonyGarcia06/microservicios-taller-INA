package com.taller.ms_pedidos.exception;

public class CantidadExcedeLimiteException extends RuntimeException {

    public CantidadExcedeLimiteException(Integer cantidadSolicitada, int limite) {
        super("No se pueden pedir " + cantidadSolicitada + " unidades: el máximo permitido por pedido es " + limite);
    }

}
