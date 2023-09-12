package com.login.loginjava.domain.service;

import com.login.loginjava.persistence.entity.Usuario;
import com.login.loginjava.web.security.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final LoginService loginService;

    public UsersDetailsService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user =  loginService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return MyUserDetails.build(user);
    }
}
