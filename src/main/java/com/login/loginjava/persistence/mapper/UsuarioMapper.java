package com.login.loginjava.persistence.mapper;

import com.login.loginjava.domain.model.UsuariosModel;
import com.login.loginjava.persistence.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {
    public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "idUsuario"),
            @Mapping(target = "userName", source = "userName"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "mail", source = "mail"),
            @Mapping(target = "sessionActive", source = "sessionActive"),
            @Mapping(target = "persona_idPersona2", source = "persona_idPersona2"),
            @Mapping(target = "status", source = "status")
    })
    public abstract UsuariosModel tousuarioModel(Usuario usuarioEntity);

    @InheritInverseConfiguration
    public abstract Usuario tousuarioEntity(UsuariosModel usuariosModel);

}
