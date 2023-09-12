package com.login.loginjava.persistence.mapper;

import com.login.loginjava.domain.model.PersonaModel;
import com.login.loginjava.persistence.entity.Persona;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PersonaMapper {

    public static final PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "idPersona"),
            @Mapping(target = "nombres", source = "nombres"),
            @Mapping(target = "apellidos", source = "apellidos"),
            @Mapping(target = "identificacion", source = "identificacion"),
            @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    })
    public abstract PersonaModel topersonaModel(Persona personaEntity);

    @InheritInverseConfiguration
    public abstract Persona topersonaEntity(PersonaModel personaModel);

}
