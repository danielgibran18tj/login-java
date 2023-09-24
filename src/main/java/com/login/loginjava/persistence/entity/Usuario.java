package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
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
    private Boolean sessionActive;

    @Column(name = "Persona_idPersona2", nullable = false, length = 15)
    private Integer personaIdPersona2;

    @ManyToOne
    @JoinColumn(name = "Persona_idPersona2", referencedColumnName = "id_persona", insertable = false, updatable = false)
    @JsonIgnore
    private Persona persona;

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL)
    private List<Rol_Usuario> rolesUsuarios;

    private String status;
}
