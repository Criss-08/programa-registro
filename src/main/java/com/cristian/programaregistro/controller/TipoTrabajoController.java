package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.TipoTrabajo;
import com.cristian.programaregistro.repository.TipoTrabajoRepository;
import com.cristian.programaregistro.service.TipoTrabajoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import java.util.List;




@RestController
@RequestMapping("/tipos-trabajo")
public class TipoTrabajoController {

    private final TipoTrabajoService service;

    public TipoTrabajoController(TipoTrabajoService service){
        this.service = service;
    }

    @GetMapping
    public List<TipoTrabajo> obtenerTodo(){
        return service.obtenerTodos();
    }

    @GetMapping("{id}")
    public ResponseEntity<TipoTrabajo> obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id)
        .map(tipoTrabajo -> ResponseEntity.ok(tipoTrabajo))
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoTrabajo> guardar(@Valid @RequestBody TipoTrabajo tipoTrabajo) {
        TipoTrabajo tipoTrabajoGuardado = service.guardar(tipoTrabajo);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTrabajoGuardado);
    }
    @PutMapping("{id}")
    public ResponseEntity<TipoTrabajo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TipoTrabajo tipoTrabajoActualizado
    ){
        return service.actualizar(id, tipoTrabajoActualizado)
        .map(actualizar -> ResponseEntity.ok(actualizar))
        .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/inactivos")
    public List<TipoTrabajo> obtenerInactivos() {
        return service.obtenerInactivos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if(service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}/reactivar")
    public ResponseEntity<TipoTrabajo> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(tipoTrabajo -> ResponseEntity.ok(tipoTrabajo))
                .orElse(ResponseEntity.notFound().build());
    }

}
