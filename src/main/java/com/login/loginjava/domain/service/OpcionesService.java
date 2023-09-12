package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.OpcionesModel;
import com.login.loginjava.persistence.crud.OpcionesRepository;
import com.login.loginjava.persistence.mapper.OpcionesMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcionesService {

    private final OpcionesRepository opcionesRepository;

    public OpcionesService(OpcionesRepository opcionesRepository) {
        this.opcionesRepository = opcionesRepository;
    }

    public List<OpcionesModel> getAll(){
        return opcionesRepository
                .findAll()
                .stream()
                .map(OpcionesMapper.INSTANCE::toopcionesModel)
                .toList();
    }

    public OpcionesModel getId(Integer idOpcion) throws ApplicationException {
        return opcionesRepository
                .findById(idOpcion)
                .map(OpcionesMapper.INSTANCE::toopcionesModel)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_OPCIONES_ROL", "opciones_rol does not exist"));
    }

    public OpcionesModel save(OpcionesModel opcionesModel) throws ApplicationException {
        if (opcionesModel.getId() == null){
            return OpcionesMapper.INSTANCE.toopcionesModel(opcionesRepository.save(OpcionesMapper.INSTANCE.torolOpcionesEntity(opcionesModel)));
        }
        throw new ApplicationException(HttpStatus.FOUND, "FOUND_OPCIONES_ROL", "idB" +
                "ranch is not null");
    }

    public OpcionesModel update(OpcionesModel opcionesModel) throws ApplicationException {
        if (exists(opcionesModel.getId())){
            var opcionesEntity1 = opcionesRepository.findById(opcionesModel.getId()).get();
            opcionesEntity1.setNombreOpcion(opcionesModel.getNombreOpcion());
            opcionesRepository.save(opcionesEntity1);
            return opcionesModel;
        }else {
            throw new ApplicationException( "NOT_FOUND_OPCIONES_ROL", "opciones_rol does not exist");
        }
    }


    public Boolean delete(int idRol) throws ApplicationException {
        if (exists(idRol)){
            opcionesRepository.deleteById(idRol);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_OPCIONES_ROL", "opciones_rol does not exist");
    }


    public boolean exists(int idRol){
        return this.opcionesRepository.existsById(idRol);
    }

}
