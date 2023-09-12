package com.login.loginjava.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Rol_RolOpcionesId implements Serializable {

    private Integer idRol;
    private Integer idOpcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol_RolOpcionesId that = (Rol_RolOpcionesId) o;
        return Objects.equals(idRol, that.idRol) && Objects.equals(idOpcion, that.idOpcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol, idOpcion);
    }
}
