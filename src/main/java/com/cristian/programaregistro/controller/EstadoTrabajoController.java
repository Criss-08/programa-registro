package com.cristian.programaregistro.controller;

import java.util.List;
import java.util.Optional;

import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.service.EstadoTrabajoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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

    @PostMapping("/estados")
    public EstadoTrabajo guardar(@RequestBody EstadoTrabajo estado){
        return service.guardar(estado);
    }

    @GetMapping("/estados/{id}")
    public Optional<EstadoTrabajo> obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @PutMapping("/estados/{id}")
    public Optional<EstadoTrabajo> actualizar(
            @PathVariable Long id,
            @RequestBody EstadoTrabajo estadoActualizado
    ){
        return service.actualizar(id, estadoActualizado);
    }

    @DeleteMapping("/estados/{id}")
    public boolean eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }



}
