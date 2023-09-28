package com.login.loginjava.domain.service;

import com.login.loginjava.persistence.crud.UsuarioRepository;
import com.login.loginjava.persistence.entity.Rol_Usuario;
import com.login.loginjava.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService { //consume los roles de la bd
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario userEntity = this.usuarioRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User "+ username +" not found "));

        String[] roles = userEntity.getRolesUsuarios().stream()
                .map(rolUsuario -> rolUsuario.getIdRol().getRolName()) // Obtener el nombre del rol desde la entidad Rol
                .toArray(String[]::new);

        return User.builder()
                .username(userEntity.getUserName())
                .password(passwordEncoder().encode(userEntity.getPassword()))
                .roles(roles)
                .accountLocked(!userEntity.getStatus())
                .disabled(!userEntity.getSessionActive())
                .build();
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
