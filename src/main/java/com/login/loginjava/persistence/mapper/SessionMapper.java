package com.login.loginjava.persistence.mapper;

import com.login.loginjava.domain.model.SessionsModel;
import com.login.loginjava.persistence.entity.Session;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class SessionMapper {

    public static final SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "idSession"),
            @Mapping(target = "fechaIngreso", source = "fechaIngreso"),
            @Mapping(target = "fechaCierre", source = "fechaCierre")
    })
    public abstract SessionsModel tosessionModel(Session sessionEntity);

    @InheritInverseConfiguration
    public abstract Session tosessionEntity(SessionsModel sessionModel);


}
