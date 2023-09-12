package com.login.loginjava.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SESSIONS")
@Getter
@Setter
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSession;

    @Column(name = "FechaIngresa", columnDefinition = "date")
    private Date fechaIngreso;

    @Column(name = "FechaCierre", columnDefinition = "date")
    private Date fechaCierre;

    @ManyToOne
    @JoinColumn(name = "usuarios_idUsuario")
    private Usuario usuario;
}
