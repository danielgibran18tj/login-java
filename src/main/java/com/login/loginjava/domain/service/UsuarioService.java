package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.UsuariosModel;
import com.login.loginjava.persistence.crud.UsuarioRepository;
import com.login.loginjava.persistence.mapper.UsuarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuariosModel> getAll(){
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioMapper.INSTANCE::tousuarioModel)
                .toList();
    }

    public UsuariosModel getId(Integer idBranch) throws ApplicationException {
        return usuarioRepository
                .findById(idBranch)
                .map(UsuarioMapper.INSTANCE::tousuarioModel)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_USUARIO", "USUARIO does not exist"));
    }



    public UsuariosModel save(UsuariosModel usuariosModel) throws ApplicationException {

        String username = usuariosModel.getUserName();
        validacionUserName(username);

        String usercontra = usuariosModel.getPassword();
        if (!usercontra.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\S)(?=.*[\\W_]).{8,}$")) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERPASSWORD", "NO estas cumpliendo con las condiciones de la contraseña");
        }

        String usercorreo = usuariosModel.getMail();
        if (usercorreo != null){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERMAIL", "Tu mail debe estar vacio para ser generado");
        }
        String generarCorreo = usuarioRepository.generarCorreoElectronico(usuariosModel.getPersona_idPersona2());

        // Guargar si se cumple todoo
        if (usuariosModel.getId() == null){
            usuariosModel.setMail(generarCorreo);
            return UsuarioMapper.INSTANCE.tousuarioModel(usuarioRepository.save(UsuarioMapper.INSTANCE.tousuarioEntity(usuariosModel)));
        }
        throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_ID", "id is not null");
    }

    public UsuariosModel update(UsuariosModel usuariosModel) throws ApplicationException {
        if (exists(usuariosModel.getId())){
            var usuarioEntity1 = usuarioRepository.findById(usuariosModel.getId()).get();
            usuarioEntity1.setUserName(usuariosModel.getUserName());
            usuarioEntity1.setPassword(usuariosModel.getPassword());
            usuarioEntity1.setMail(usuariosModel.getMail());
            usuarioEntity1.setSessionActive(usuariosModel.getSessionActive());
            usuarioEntity1.setStatus(usuariosModel.getStatus());
            usuarioRepository.save(usuarioEntity1);
            return usuariosModel;
        }else {
            throw new ApplicationException( "NOT_FOUND_USUARIO", "USUARIO does not exist");
        }
    }


    public Boolean delete(int idUsuario) throws ApplicationException {
        if (exists(idUsuario)){
            usuarioRepository.deleteById(idUsuario);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_USUARIO", "USUARIO does not exist");
    }


    public boolean exists(int idUsuario){
        return this.usuarioRepository.existsById(idUsuario);
    }


    public Boolean validacionUserName(String username) throws ApplicationException {

        // no estar vacio
        if (username == null || username.isEmpty()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "El nombre de usuario no puede estar vacío");
        }

        //longitud maxima 20 y minima 8
        if (username.length() < 8 || username.length() > 20) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "La longitud del nombre de usuario debe estar entre 8 y 20 caracteres");
        }

        //no contener signos
        if (!username.matches("^[a-zA-Z0-9]*$")) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "El nombre de usuario no debe contener signos");
        }

        //no duplicar nombre
        if (usuarioRepository.existsByUserName(username)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "DUPLICATE_USERNAME", "El nombre de usuario ya está en uso");
        }

        // al menos un numero
        if (!username.matches(".*\\d.*")) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "El nombre de usuario debe contener al menos un número");
        }

        // al menos una letra
        if (!username.matches(".*[A-Z].*")) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "El nombre de usuario debe contener al menos una letra mayúscula");
        }
        return true;
    }
}
