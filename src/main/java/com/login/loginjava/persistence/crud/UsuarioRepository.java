package com.login.loginjava.persistence.crud;

import com.login.loginjava.domain.model.UsuariosModel;
import com.login.loginjava.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT GenerarCorreoElectronico(p.nombres, p.apellidos, p.identificacion) " +
            "FROM Persona p WHERE p.idPersona = :idPersona")
    String generarCorreoElectronico(@Param("idPersona") Integer idPersona);

    boolean existsByUserName(String userName);

    /*DELIMITER $$

CREATE FUNCTION GenerarCorreoElectronico(nombres VARCHAR(255), apellidos VARCHAR(255), identificacion VARCHAR(255))
RETURNS VARCHAR(255)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE correoBase VARCHAR(255);
    DECLARE correo VARCHAR(255);
    DECLARE contador INT DEFAULT 0;

    SET correoBase = CONCAT(SUBSTRING(nombres, 1, 1), apellidos) COLLATE utf8mb4_unicode_ci;

    REPEAT
        SET contador = contador + 1;
        SET correo = CONCAT(correoBase, contador, '@mail.com') COLLATE utf8mb4_unicode_ci;
    UNTIL NOT EXISTS (SELECT 1 FROM Usuarios WHERE mail = correo) END REPEAT;

    RETURN correo;
END$$

DELIMITER ;
*/

}
