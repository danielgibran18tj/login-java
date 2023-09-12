package com.login.loginjava.persistence.mapper;

import com.login.loginjava.domain.model.RolModel;
import com.login.loginjava.persistence.entity.Rol;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class RolMapper {

    public static final RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "idRol"),
            @Mapping(target = "rolName", source = "rolName"),

    })
    public abstract RolModel torolModel(Rol rolEntity);

    @InheritInverseConfiguration
    public abstract Rol torolEntity(RolModel rolModel);


}
