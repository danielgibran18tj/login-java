package com.login.loginjava.domain.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.login.loginjava.persistence.entity.Persona;
import com.login.loginjava.persistence.entity.Rol_Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuariosModel {

    private Integer id;

    private String userName;
    private String password;
    private String mail;
    private String sessionActive;
    //private List<Persona> Persona_idPersona2;
    private String status;

}
