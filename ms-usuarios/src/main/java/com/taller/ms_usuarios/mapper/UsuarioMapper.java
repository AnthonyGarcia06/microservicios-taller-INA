package com.taller.ms_usuarios.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UsuarioMapper {

    //convierte el response dto a entity
    @Mapping(target = "id" , ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    //convierte de entity a responseDTO
    UsuarioResponseDTO toResponse(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            @MappingTarget Usuario usuario,
            UsuarioRequestDTO dto
    );

    List<UsuarioResponseDTO> toReponseDTOList(List<Usuario> usuarios);

}
