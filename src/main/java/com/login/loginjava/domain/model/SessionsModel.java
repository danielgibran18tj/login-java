package com.login.loginjava.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SessionsModel {
    private Integer id;

    private Date fechaIngreso;
    private Date fechaCierre;

}
