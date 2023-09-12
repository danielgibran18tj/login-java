package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
    private Usuario usuario;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "Rol_idRol")
    private Rol rol;

}
