package com.login.loginjava.web.controller;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.dto.User;
import com.login.loginjava.domain.model.JWT;
import com.login.loginjava.domain.service.LoginService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    public LoginController(AuthenticationManager authenticationManager, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    public ResponseEntity<?> loginUser(@ApiParam(value = "Login user object" ,required=true ) @RequestBody User body) throws ApplicationException {

        var user = loginService.login(body.getUsername(), body.getPassword());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        authenticationManager.authenticate(authentication);

        return new ResponseEntity<JWT>(loginService.createToken(user), HttpStatus.OK);
    }

    public ResponseEntity<?> loginOut(@ApiParam(value = "Login user object" ,required=true ) @RequestBody User body) throws ApplicationException {

        loginService.logout(body.getUsername());

        return new ResponseEntity(HttpStatus.OK);
    }

}
