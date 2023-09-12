package com.login.loginjava.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rol_rolOpciones")
@Getter
@Setter
@NoArgsConstructor
public class Rol_RolOpciones {
    @EmbeddedId
    private Rol_RolOpcionesId id;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "rol_idRol")
    private Rol idRol;

    @ManyToOne
    @MapsId("idOpcion")
    @JoinColumn(name = "rolOpciones_idOpcion")
    private RolOpciones idOpcion;
}
