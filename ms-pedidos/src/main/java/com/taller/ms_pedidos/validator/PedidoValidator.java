package com.taller.ms_pedidos.validator;

import com.taller.ms_pedidos.exception.CantidadExcedeLimiteException;
import com.taller.ms_pedidos.exception.LimitePedidosActivosException;
import com.taller.ms_pedidos.exception.PedidoYaCanceladoException;
import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import com.taller.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private static final int CANTIDAD_MAXIMA_POR_PEDIDO = 20;
    private static final int LIMITE_PEDIDOS_CONFIRMADOS = 5;

    private final PedidoRepository pedidoRepository;

    // Regla 1: no se puede pedir mas de 20 unidades de un mismo producto.
    // Es una regla "interna" -- no necesita hablar con ningun otro microservicio.
    public void validarCantidadMaxima(Integer cantidad) {
        if (cantidad > CANTIDAD_MAXIMA_POR_PEDIDO) {
            throw new CantidadExcedeLimiteException(cantidad, CANTIDAD_MAXIMA_POR_PEDIDO);
        }
    }

    // Regla 2: un usuario no puede tener mas de 5 pedidos CONFIRMADO al mismo tiempo.
    // Tambien es interna: solo consulta la base propia de ms-pedidos, no llama a ms-usuarios.
    public void validarLimitePedidosActivos(Long usuarioId) {
        long pedidosConfirmados = pedidoRepository.countByUsuarioIdAndEstado(usuarioId, EstadoPedido.CONFIRMADO);

        if (pedidosConfirmados >= LIMITE_PEDIDOS_CONFIRMADOS) {
            throw new LimitePedidosActivosException(usuarioId, LIMITE_PEDIDOS_CONFIRMADOS);
        }
    }

    // Regla 3: no se puede cancelar un pedido que ya esta CANCELADO.
    public void validarNoCancelado(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new PedidoYaCanceladoException(pedido.getId());
        }
    }

}
