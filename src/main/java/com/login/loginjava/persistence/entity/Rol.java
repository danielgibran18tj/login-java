package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
public class Rol {
    @Id
    @Column(name = "idRol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(name = "rolName", length = 50)
    private String rolName;

    @OneToMany(mappedBy = "idRol", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rol_Usuario> rolesNames;


    @OneToMany(mappedBy = "idRol", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rol_RolOpciones> rolOpciones;
}
