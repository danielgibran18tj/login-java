package com.login.loginjava.persistence.crud;

import com.login.loginjava.domain.model.UsuariosModel;
import com.login.loginjava.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
}
