package com.login.loginjava.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rol_usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Rol_Usuario {
    @EmbeddedId
    private Rol_UsuarioId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "usuarios_idUsuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "Rol_idRol")
    private Rol rol;

}
