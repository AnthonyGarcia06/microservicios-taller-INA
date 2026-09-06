package com.taller.ms_usuarios.common.exception;

public class UsuarioNotFoundException extends RuntimeException{


    public UsuarioNotFoundException(Long id) {
        super("No se encontro usuario con id: "+id );
    }


}
