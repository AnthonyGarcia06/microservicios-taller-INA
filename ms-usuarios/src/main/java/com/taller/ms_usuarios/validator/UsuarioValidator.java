package com.taller.ms_usuarios.validator;
import com.taller.ms_usuarios.common.exception.EmailDuplicadoException;
import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Component;

/*

Aqui solo reglas de negocio para usuarios

*/
@Data
@AllArgsConstructor
@Component
public class UsuarioValidator {

    //inyeccion de dependencia
    private final UsuarioRepository usuarioRepository;

    //validar el correo
    public void checkEmailUniqueCreate(String email){

        if(usuarioRepository.existsByEmail(email)){

            throw new EmailDuplicadoException(email);
        }

    }

    //validar el correo
    public void checkEmailActualizar(Usuario usuarioActual, String email){

        boolean cambiarEmail= !usuarioActual.getEmail().equalsIgnoreCase(email);

        if(cambiarEmail && usuarioRepository.existsByEmail(email) ){
            throw new EmailDuplicadoException(email);
        }

    }

}
