package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.PersonaModel;
import com.login.loginjava.persistence.crud.PersonaRepository;
import com.login.loginjava.persistence.mapper.PersonaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    public List<PersonaModel> getAll(){
        return personaRepository
                .findAll()
                .stream()
                .map(PersonaMapper.INSTANCE::topersonaModel)
                .toList();
    }

    public PersonaModel getId(Integer idpersona) throws ApplicationException {
        return personaRepository
                .findById(idpersona)
                .map(PersonaMapper.INSTANCE::topersonaModel)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_PEOPLE", "PEOPLE does not exist"));
    }

    public PersonaModel save(PersonaModel personaModel) throws ApplicationException {


        String identificacionuser = personaModel.getIdentificacion();
        if (!identificacionuser.matches("^(?!.*(\\d)(?:\\1{3,})).{10}$")){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "INVALID_USERIDENTIFICATION", "NO estas cumpliendo con las condiciones de la identificacion");
        }

        if (personaModel.getId() == null){
            return PersonaMapper.INSTANCE.topersonaModel(personaRepository.save(PersonaMapper.INSTANCE.topersonaEntity(personaModel)));
        }
        throw new ApplicationException(HttpStatus.FOUND, "FOUND_PEOPLE", "id persona exist");
    }

    public PersonaModel update(PersonaModel personaModel) throws ApplicationException {
        if (exists(personaModel.getId())){
            var personaEntity1 = personaRepository.findById(personaModel.getId()).get();
            personaEntity1.setNombres(personaModel.getNombres());
            personaEntity1.setApellidos(personaModel.getApellidos());
            personaEntity1.setIdentificacion(personaModel.getIdentificacion());
            personaEntity1.setFechaNacimiento(personaModel.getFechaNacimiento());
            personaRepository.save(personaEntity1);
            return personaModel;
        }else {
            throw new ApplicationException( HttpStatus.FOUND, "NOT_FOUND_PEOPLE", "people does not exist");
        }
    }


    public Boolean delete(int idpersona) throws ApplicationException {
        if (exists(idpersona)){
            personaRepository.deleteById(idpersona);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_PEOPLE", "PEOPLE does not exist");
    }


    public boolean exists(int idPersona){
        return this.personaRepository.existsById(idPersona);
    }


}
