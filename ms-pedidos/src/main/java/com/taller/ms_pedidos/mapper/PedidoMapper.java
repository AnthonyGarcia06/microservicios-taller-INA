package com.taller.ms_pedidos.mapper;

import com.taller.ms_pedidos.dto.PedidoDTO;
import com.taller.ms_pedidos.dto.PedidoRequestDTO;
import com.taller.ms_pedidos.dto.PedidoResponseDTO;
import com.taller.ms_pedidos.dto.ProductoDTO;
import com.taller.ms_pedidos.dto.UsuarioDTO;
import com.taller.ms_pedidos.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Pedido toEntity(PedidoRequestDTO dto);

    PedidoDTO toDTO(Pedido pedido);

    List<PedidoDTO> toDTOList(List<Pedido> pedidos);

    @Mapping(target = "id", source = "pedido.id")
    @Mapping(target = "usuario", source = "usuarioDTO")
    @Mapping(target = "producto", source = "productoDTO")
    PedidoResponseDTO toResponse(Pedido pedido, UsuarioDTO usuarioDTO, ProductoDTO productoDTO);

}
