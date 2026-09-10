package com.taller.ms_pedidos.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(Long id) {
        super("No se encontró pedido con id: " + id);
    }

}
