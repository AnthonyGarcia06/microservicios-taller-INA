package com.taller.ms_pedidos.exception;

public class PedidoYaCanceladoException extends RuntimeException {

    public PedidoYaCanceladoException(Long id) {
        super("El pedido " + id + " ya está CANCELADO");
    }

}
