package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.RolModel;
import com.login.loginjava.persistence.crud.RolRepository;
import com.login.loginjava.persistence.mapper.RolMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolModel> getAll(){
        return rolRepository
                .findAll()
                .stream()
                .map(RolMapper.INSTANCE::torolModel)
                .toList();
    }

    public RolModel getId(Integer idRol) throws ApplicationException {
        return rolRepository
                .findById(idRol)
                .map(RolMapper.INSTANCE::torolModel)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_ROL", "rol does not exist"));
    }

    public RolModel save(RolModel rolModel) throws ApplicationException {
        if (rolModel.getId() == null){
            return RolMapper.INSTANCE.torolModel(rolRepository.save(RolMapper.INSTANCE.torolEntity(rolModel)));
        }
        throw new ApplicationException(HttpStatus.FOUND, "FOUND_ROL", "idB" +
                "ranch is not null");
    }

    public RolModel update(RolModel rolModel) throws ApplicationException {
        if (exists(rolModel.getId())){
            var rolEntity1 = rolRepository.findById(rolModel.getId()).get();
            rolEntity1.setRolName(rolModel.getRolName());
            rolRepository.save(rolEntity1);
            return rolModel;
        }else {
            throw new ApplicationException( "NOT_FOUND_ROL", "ROL does not exist");
        }
    }


    public Boolean delete(int idRol) throws ApplicationException {
        if (exists(idRol)){
            rolRepository.deleteById(idRol);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_ROL", "rol does not exist");
    }


    public boolean exists(int idRol){
        return this.rolRepository.existsById(idRol);
    }

}
