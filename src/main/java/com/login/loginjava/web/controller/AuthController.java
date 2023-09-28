package com.login.loginjava.web.controller;

import com.login.loginjava.domain.dto.LoginDto;
import com.login.loginjava.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login); //Dao Authentication Provider
        //hasta aqui llega, en caso de no estar autenticado
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());  //usuario que inicio sesion

        String token = this.jwtUtil.create(loginDto.getUsername());
        System.out.println("si esta pasando por aqui " + token);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,token).build();
    }
}