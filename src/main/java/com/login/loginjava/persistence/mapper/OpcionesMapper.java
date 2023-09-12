package com.login.loginjava.persistence.mapper;

import com.login.loginjava.domain.model.OpcionesModel;
import com.login.loginjava.persistence.entity.RolOpciones;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class OpcionesMapper {
    public static final OpcionesMapper INSTANCE = Mappers.getMapper(OpcionesMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "idOpcion"),
            @Mapping(target = "nombreOpcion", source = "nombreOpcion")
    })
    public abstract OpcionesModel toopcionesModel(RolOpciones rolOpcionesEntity);

    @InheritInverseConfiguration
    public abstract RolOpciones torolOpcionesEntity(OpcionesModel opcionesModel);

}
