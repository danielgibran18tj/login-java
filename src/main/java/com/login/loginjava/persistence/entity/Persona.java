package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
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
    @Column(name = "id_persona", nullable = false)
    private Integer idPersona;

    @Column(name = "Nombres", length = 60)
    private String nombres;

    @Column(name = "Apellidos", length = 60)
    private String apellidos;

    @Column(name = "Identificacion", length = 10)
    private String identificacion;

    @Column(name = "FechaNacimiento", columnDefinition = "date")
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "persona", fetch = FetchType.EAGER)
    private List<Usuario> usuarios;
}
