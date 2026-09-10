package com.taller.ms_pedidos.exception;

public class LimitePedidosActivosException extends RuntimeException {

    public LimitePedidosActivosException(Long usuarioId, int limite) {
        super("El usuario " + usuarioId + " ya tiene " + limite + " pedidos CONFIRMADO activos (el máximo permitido)");
    }

}
