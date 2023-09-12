package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.JWT;
import com.login.loginjava.persistence.crud.SessionRepository;
import com.login.loginjava.persistence.crud.UsuarioRepository;
import com.login.loginjava.persistence.entity.Session;
import com.login.loginjava.persistence.entity.Usuario;
import com.login.loginjava.web.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoginService {

    private static final int EXPIRATION_TIME = 1000 * 60 * 60;
    private static final String AUTHORITIES = "authorities";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private final String SECRET_KEY;

    private final UsuarioRepository usuarioRepository;
    private final SessionRepository sessionRepository;

    public LoginService(UsuarioRepository usuarioRepository, SessionRepository sessionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessionRepository = sessionRepository;

        SECRET_KEY = Base64.getEncoder().encodeToString("wRAhaK8aGMK43c3pOA9CVMQa5R3mlfR+HUJQjubRMBzpcsKOgn+HMApLzUDhyCh6HnnmOmU0BSJwB1Z/eqDLxA==".getBytes());
    }

    public Usuario login(String username, String password) throws ApplicationException {
        var existsUser = usuarioRepository.findUsuarioByUserName(username);
        if (existsUser.isPresent()) {
            var user = existsUser.get();

            if (user.getStatus().equals("B"))
                throw new ApplicationException(HttpStatus.CONFLICT, "002", "usuario bloquedo");

            var validPassword = Objects.equals(user.getPassword(), password);
            if (validPassword) {
                if (user.getSessionActive().equals("A")) {
                    throw new ApplicationException(HttpStatus.CONFLICT, "003", "usuario ya tiene una sesion activa");
                }
                user.setSessionActive("A");
                user.setRetry(0);
                var session = new Session();
                Date date = new Date();
                session.setUsuario(user);
                session.setFechaIngreso(date);
                sessionRepository.save(session);
            } else {
                user.setRetry(user.getRetry() + 1);
                if (user.getRetry() >= 3) {
                    user.setStatus("B");
                }
            }
            usuarioRepository.save(user);

            return user;
        }

        throw new ApplicationException(HttpStatus.UNAUTHORIZED, "001", "Usuario y/o contraseña incorrecta");
    }

    public void logout(String username) throws ApplicationException {
        var existsUser = usuarioRepository.findUsuarioByUserName(username);
        if (existsUser.isPresent()) {
            var user = existsUser.get();

            user.setSessionActive("I");
            var session = sessionRepository.findByUsuarioUserName(username);
            if (session.isPresent()) {
                var s = session.get();
                Date date = new Date();
                s.setFechaCierre(date);
                sessionRepository.save(s);
            } else {
                throw new ApplicationException(HttpStatus.CONFLICT, "004", "Session no existe");
            }
            usuarioRepository.save(user);
        }
        throw new ApplicationException(HttpStatus.UNAUTHORIZED, "001", "Usuario y/o contraseña incorrecta");
    }

    public JWT createToken(Usuario user) {
        UserDetails userDetails = MyUserDetails.build(user);
        String username = userDetails.getUsername();

        String firstName = user.getPersona().getNombres();
        String lastName = user.getPersona().getApellidos();
        String email = user.getMail();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        JWT jwt = new JWT();
        jwt.setJwt(Jwts.builder()
                .setSubject(username)
                .claim(FIRST_NAME,firstName)
                .claim(LAST_NAME,lastName)
                .claim(EMAIL, email)
                .claim(AUTHORITIES, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact());
        return jwt;
    }

    public Boolean hasTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (userDetails.getUsername().equals(username) && !hasTokenExpired(token));

    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return (Collection<? extends GrantedAuthority>) claims.get(AUTHORITIES);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findUsuarioByUserName(username);
    }
}
