package com.cristian.programaregistro.controller;

import java.util.List;

import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.service.EstadoTrabajoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class EstadoTrabajoController {

    private final EstadoTrabajoService service;

    public EstadoTrabajoController(EstadoTrabajoService service) {
        this.service = service;
    }
    @GetMapping("/estados")
    public List<EstadoTrabajo> obtenerTodos(){
        return service.obtenerTodos();
    }

}
