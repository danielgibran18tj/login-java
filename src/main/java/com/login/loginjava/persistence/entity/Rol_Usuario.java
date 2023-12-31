package com.login.loginjava.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private Usuario idUsuario;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "Rol_idRol")
    private Rol idRol;

}
