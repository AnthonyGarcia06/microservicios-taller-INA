package com.taller.ms_pedidos.repository;

import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    long countByUsuarioIdAndEstado(Long usuarioId, EstadoPedido estado);

}
