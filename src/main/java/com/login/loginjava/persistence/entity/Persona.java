package com.login.loginjava.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PERSONA")
@Getter
@Setter
@NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    private String nombres;
    private String apellidos;
    private String identificacion;
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "persona")
    private List<Usuario> usuarios;
}
