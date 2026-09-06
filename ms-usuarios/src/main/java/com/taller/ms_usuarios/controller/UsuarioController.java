package com.taller.ms_usuarios.controller;

import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioservice;

    //en un request puedo mandar datos por:
    //body: @RequesBody
    //url: @PathVariable

//    @GetMapping("/test")
//    @ResponseStatus(HttpStatus.OK)
//    public String obtener(){
//
//        return "aqui estoy";
//    }
//
//    @GetMapping("/test/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public String obtenerById(@PathVariable Long id){
//
//        return "aqui estoy "+ id.toString();
//    }

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){

        //recibo dto y requiero mapear a entity


        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.crear(usuarioDTO));
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDTO>>listar(){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioservice.listarTodos());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO>listarPorId(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioservice.listarPorId(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO>modificar(@PathVariable Long id,@Valid @RequestBody UsuarioRequestDTO usuarioDTO){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioservice.actualizar(id,usuarioDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO>Eliminar(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioservice.eliminar(id));

    }

}
