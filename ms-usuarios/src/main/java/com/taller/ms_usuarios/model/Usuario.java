package com.taller.ms_usuarios.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "tbUsuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El bombre es obligatorio")
    @Size(min= 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column (nullable = false)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    @Column (nullable = false, unique = true)
    private String email;

    @Size(min= 2, max = 80, message = "La ciudad debe tener entre 2 y 80 caracteres")
    private String ciudad;


}//finclass
