package com.taller.ms_usuarios.service;

import com.taller.ms_usuarios.common.exception.UsuarioNotFoundException;
import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.mapper.UsuarioMapper;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import com.taller.ms_usuarios.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuariorepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

    public List<UsuarioResponseDTO> listarTodos(){
        List<Usuario> lista= usuariorepository.findAll();
        return usuarioMapper.toReponseDTOList(lista);
    }

    public UsuarioResponseDTO listarPorId(Long id){

        Usuario usuario = usuariorepository.findById(id).orElseThrow(()->new UsuarioNotFoundException(id));

        //mapeo
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDTO crear(UsuarioRequestDTO usuarioDTO){

        //reglas de negocio, usando las excepciones personalizadas
        usuarioValidator.checkEmailUniqueCreate(usuarioDTO.getEmail());

        //mapear dto a entity para enviar y guardar
//        Usuario usuario= new Usuario();
//
//        usuario.setNombre(usuarioDTO.getNombre());
//        usuario.setCiudad(usuarioDTO.getCiudad());
//        usuario.setEmail(usuarioDTO.getEmail());

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuariorepository.save(usuario);

        //despues de guardar mapper de entity a response dto
        //UsuarioResponseDTO usuarioResponse = new UsuarioResponseDTO();
        //usuarioResponse.setId(usuario.getId());
        //usuarioResponse.setNombre(usuario.getNombre());
       // usuarioResponse.setCiudad(usuario.getCiudad());
        //usuarioResponse.setEmail(usuario.getEmail());

        UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponse(usuario);
        return usuarioResponse;
    }

    public UsuarioResponseDTO actualizar (Long id, UsuarioRequestDTO usuarioDTO){

        //validar que exista
        Usuario usuarioActualBD = usuariorepository.findById(id).orElseThrow(()->new UsuarioNotFoundException(id));

        //regla de negocios validar email
        usuarioValidator.checkEmailActualizar(usuarioActualBD,usuarioDTO.getEmail());

        //mapeo
        usuarioMapper.updateEntity(usuarioActualBD,usuarioDTO);
        usuarioActualBD = usuariorepository.save(usuarioActualBD);


        return usuarioMapper.toResponse(usuarioActualBD);
    }

    public UsuarioResponseDTO eliminar (Long id){

        Usuario usuarioActualBD = usuariorepository.findById(id).orElseThrow(()->new UsuarioNotFoundException(id));
        usuariorepository.delete(usuarioActualBD);
        return usuarioMapper.toResponse(usuarioActualBD);
    }

}
