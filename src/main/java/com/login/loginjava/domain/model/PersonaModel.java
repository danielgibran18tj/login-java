package com.login.loginjava.domain.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PersonaModel {

    private Integer id;
    private String nombres;
    private String apellidos;
    private String identificacion;
    private Date fechaNacimiento;

}
