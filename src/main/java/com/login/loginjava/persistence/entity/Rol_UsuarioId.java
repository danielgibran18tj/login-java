package com.login.loginjava.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Rol_UsuarioId implements Serializable {
    private Integer idUsuario;
    private Integer idRol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol_UsuarioId that = (Rol_UsuarioId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idRol, that.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idRol);
    }
}
