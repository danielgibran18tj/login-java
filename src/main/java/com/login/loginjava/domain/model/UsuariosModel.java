package com.login.loginjava.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuariosModel {

    private Integer id;

    private String userName;
    private String password;
    private String mail;
    private Boolean sessionActive;
    private Integer persona_idPersona2;
    //private List<Persona> Persona_idPersona2;
    private Boolean status;

}
