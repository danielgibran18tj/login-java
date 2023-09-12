package com.login.loginjava.web.controller;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.PersonaModel;
import com.login.loginjava.domain.service.PersonaService;
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
@RequestMapping("/api/persona")
public class PersonaController {

    private static final Logger log = LoggerFactory.getLogger(PersonaController.class);
    private final HttpServletRequest request;
    private final PersonaService personaService;

    public PersonaController(HttpServletRequest request, PersonaService personaService) {
        this.request = request;
        this.personaService = personaService;
    }



    @Operation(summary = "Find all people")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PersonaModel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "people not found") })
    @RequestMapping(value = "/persona",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<PersonaModel>> getAll() {
        try {
            return new ResponseEntity<List<PersonaModel>>(personaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<PersonaModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Find people")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PersonaModel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "people not found") })
    @RequestMapping(value = "/persona/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<PersonaModel> getId(@PathVariable("id")Integer id) throws ApplicationException {
        String accept = request.getHeader("Accept");
        if (accept!= null && accept.contains("application/json")){
            return ResponseEntity.ok(personaService.getId(id));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }



    @Operation(summary = "Add a new people to the db")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/persona",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<PersonaModel> agg(@RequestBody PersonaModel personaModel) throws ApplicationException {
        PersonaModel personaModel1 = personaService.save(personaModel);
        if (personaModel1 != null){
            return ResponseEntity.ok(personaModel1);
        }
        return ResponseEntity.badRequest().build();
    }



    @Operation(summary = "Update an existing people")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "people not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/persona",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    public ResponseEntity<PersonaModel> update(@RequestBody PersonaModel personaModel) throws ApplicationException {
        PersonaModel personaModel1 = personaService.update(personaModel);
        if (personaModel1 != null){
            return ResponseEntity.ok(personaModel1);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Delete an existing people")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "people not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/persona/{idPersona}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("idPersona") int idPersona) throws ApplicationException {
        Boolean persona1 = personaService.delete(idPersona);
        if (persona1){
            return ResponseEntity.ok("people successfully removed");
        }
        return ResponseEntity.badRequest().build();
    }

}
