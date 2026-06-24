package com.cristian.programaregistro.controller;

import java.util.List;
import java.util.Optional;

import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.service.EstadoTrabajoService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoTrabajoController {

    private final EstadoTrabajoService service;

    public EstadoTrabajoController(EstadoTrabajoService service) {
        this.service = service;
    }
    @GetMapping
    public List<EstadoTrabajo> obtenerTodos(){
        return service.obtenerTodos();
    }

    @PostMapping
    public EstadoTrabajo guardar(@Valid @RequestBody EstadoTrabajo estado){
        return service.guardar(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoTrabajo> obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id)
                .map(estado -> ResponseEntity.ok(estado))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<EstadoTrabajo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstadoTrabajo estadoActualizado
    ){
        return service.actualizar(id, estadoActualizado)
        .map(estado -> ResponseEntity.ok(estado))
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if(service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
