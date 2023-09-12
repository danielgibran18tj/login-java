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
        if (usuariosModel.getId() == null){
            return UsuarioMapper.INSTANCE.tousuarioModel(usuarioRepository.save(UsuarioMapper.INSTANCE.tousuarioEntity(usuariosModel)));
        }
        throw new ApplicationException(HttpStatus.FOUND, "FOUND_USUARIO", "idB" +
                "ranch is not null");
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


}
