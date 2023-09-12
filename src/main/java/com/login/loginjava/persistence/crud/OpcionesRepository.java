package com.login.loginjava.persistence.crud;

import com.login.loginjava.persistence.entity.RolOpciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpcionesRepository extends JpaRepository<RolOpciones, Integer> {
}
