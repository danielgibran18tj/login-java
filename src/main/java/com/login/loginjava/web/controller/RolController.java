package com.login.loginjava.web.controller;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.RolModel;
import com.login.loginjava.domain.service.RolService;
import com.login.loginjava.persistence.entity.Rol;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol")
public class RolController {

    private static final Logger log = LoggerFactory.getLogger(RolController.class);
    private final HttpServletRequest request;
    private final RolService rolService;

    public RolController(HttpServletRequest request, RolService rolService) {
        this.request = request;
        this.rolService = rolService;
    }

    @Operation(summary = "Find all rol")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Rol.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Rol not found") })
    @RequestMapping(value = "/rol",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<RolModel>> getAll() {
        try {
            return new ResponseEntity<List<RolModel>>(rolService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<RolModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Find rol")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = RolModel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "rol not found") })
    @RequestMapping(value = "/rol/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<RolModel> getId(@PathVariable("id")Integer id) throws ApplicationException {
        String accept = request.getHeader("Accept");
        if (accept!= null && accept.contains("application/json")){
            return ResponseEntity.ok(rolService.getId(id));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }



    @Operation(summary = "Add a new rol to the db")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/rol",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<RolModel> agg(@RequestBody RolModel rolModel) throws ApplicationException {
        RolModel rolModel1 = rolService.save(rolModel);
        try {
            if (rolModel1 != null){
                return ResponseEntity.ok(rolModel1);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }



    @Operation(summary = "Update an existing rol")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Rol not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/rol",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    public ResponseEntity<RolModel> update(@RequestBody RolModel rolModel) throws ApplicationException {
        RolModel rolModel1 = rolService.update(rolModel);
        if (rolModel1 != null){
            return ResponseEntity.ok(rolModel1);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Delete an existing rol")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "rol not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/rol/{idRol}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(int idRol) throws ApplicationException {
        Boolean rol1 = rolService.delete(idRol);
        if (rol1){
            return ResponseEntity.ok("Rol successfully removed");
        }
        return ResponseEntity.badRequest().build();
    }

}
