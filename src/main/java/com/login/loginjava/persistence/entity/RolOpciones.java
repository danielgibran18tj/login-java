package com.login.loginjava.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rol_opciones")
@Getter
@Setter
@NoArgsConstructor
public class RolOpciones {
    @Id
    @javax.persistence.Id
    @Column(name = "idOpcion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOpcion;

    private String nombreOpcion;

    @OneToMany(mappedBy = "idOpcion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rol_RolOpciones> rol;

}
