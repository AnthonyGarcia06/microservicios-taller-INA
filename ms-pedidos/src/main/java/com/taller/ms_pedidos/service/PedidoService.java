package com.taller.ms_pedidos.service;

import com.taller.ms_pedidos.client.ProductoClient;
import com.taller.ms_pedidos.client.UsuarioClient;
import com.taller.ms_pedidos.dto.*;
import com.taller.ms_pedidos.exception.PedidoNotFoundException;
import com.taller.ms_pedidos.mapper.PedidoMapper;
import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import com.taller.ms_pedidos.repository.PedidoRepository;
import com.taller.ms_pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoValidator pedidoValidator;
    private final UsuarioClient usuarioClient;
    private final ProductoClient productoClient;


    public List<PedidoDTO> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toDTOList(pedidos);
    }


    public PedidoResponseDTO listarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return enriquecer(pedido);
    }

    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {


        pedidoValidator.validarCantidadMaxima(request.getCantidad());


        pedidoValidator.validarLimitePedidosActivos(request.getUsuarioId());


        UsuarioDTO usuario = usuarioClient.obtenerUsuario(request.getUsuarioId());


        ProductoDTO producto = productoClient.obtenerProducto(request.getProductoId());

        BigDecimal total = producto.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad()));

        productoClient.descontarStock(request.getProductoId(), request.getCantidad());

        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setTotal(total);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.CONFIRMADO);
        pedido = pedidoRepository.save(pedido);


        return pedidoMapper.toResponse(pedido, usuario, producto);
    }

    public PedidoResponseDTO cancelarPedido(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        pedidoValidator.validarNoCancelado(pedido);


        productoClient.reponerStock(pedido.getProductoId(), pedido.getCantidad());

        pedido.setEstado(EstadoPedido.CANCELADO);
        pedido = pedidoRepository.save(pedido);

        return enriquecer(pedido);
    }

    private PedidoResponseDTO enriquecer(Pedido pedido) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(pedido.getUsuarioId());
        ProductoDTO producto = productoClient.obtenerProducto(pedido.getProductoId());
        return pedidoMapper.toResponse(pedido, usuario, producto);
    }

}
