package com.login.loginjava.web.controller;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.UsuariosModel;
import com.login.loginjava.domain.service.UsuarioService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    private final HttpServletRequest request;
    private final UsuarioService usuarioService;

    public UsuarioController(HttpServletRequest request, UsuarioService usuarioService) {
        this.request = request;
        this.usuarioService = usuarioService;
    }


    @Operation(summary = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = UsuariosModel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Users not found") })
    @RequestMapping(value = "/usuario",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<UsuariosModel>> getAll(){
        try {
            return new ResponseEntity<List<UsuariosModel>>(usuarioService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<UsuariosModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Find user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = UsuariosModel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "user not found") })
    @RequestMapping(value = "/usuario/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<UsuariosModel> getId(@ApiParam(value = "ID of user to return", required = true) @PathVariable("id") Integer id) throws ApplicationException{
        String accept = request.getHeader("Accept");
        if (accept!= null && accept.contains("application/json")){
            return ResponseEntity.ok(usuarioService.getId(id));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "Add a new user to the db")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/usuario",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<UsuariosModel> agg(@RequestBody UsuariosModel usuariosModel) throws ApplicationException{
        UsuariosModel usuariosModel1 = usuarioService.save(usuariosModel);
        if (usuariosModel1 != null){
            return ResponseEntity.ok(usuariosModel1);
        }return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/usuario",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    public ResponseEntity<UsuariosModel> update(@RequestBody UsuariosModel usuariosModel) throws ApplicationException{
        UsuariosModel usuariosModel1 = usuarioService.update(usuariosModel);
        if (usuariosModel1 != null){
            return ResponseEntity.ok(usuariosModel1);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "user not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/usuario/{idUsuario}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@ApiParam(value = "ID of user", required = true) @PathVariable("idUsuario") int idUsuario) throws ApplicationException{
        Boolean usuario1 = usuarioService.delete(idUsuario);
        if (usuario1){
            return ResponseEntity.ok("User successfully removed");
        }
        return ResponseEntity.badRequest().build();
    }


}
