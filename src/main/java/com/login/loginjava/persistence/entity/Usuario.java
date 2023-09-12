package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String userName;
    private String password;
    private String mail;
    private String sessionActive;


    @Column(name = "persona_idPersona", nullable = false, length = 15)
    private Integer persona_idPersona2;
    @ManyToOne
    @JoinColumn(name = "Persona_idPersona2")
    private Persona persona;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rol_Usuario> rolesUsuarios;

    private String status;
}
