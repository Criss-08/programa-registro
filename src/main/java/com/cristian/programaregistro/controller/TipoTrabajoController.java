package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.TipoTrabajo;
import com.cristian.programaregistro.repository.TipoTrabajoRepository;
import com.cristian.programaregistro.service.TipoTrabajoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

@RestController
public class TipoTrabajoController {

    private final TipoTrabajoService service;

    public TipoTrabajoController(TipoTrabajoService service){
        this.service = service;
    }

    @GetMapping("/tipos-trabajo")
    public List<TipoTrabajo> obtenerTodo(){
        return service.obtenerTodos();
    }

    @GetMapping("/tipos-trabajo/{id}")
    public Optional<TipoTrabajo> obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @PostMapping("/tipos-trabajo")
    public TipoTrabajo guardar(@RequestBody TipoTrabajo tipoTrabajo){
        return service.guardar(tipoTrabajo);
    }

    @PutMapping("/tipos-trabajo/{id}")
    public Optional<TipoTrabajo> actualizar(
            @PathVariable Long id,
            @RequestBody TipoTrabajo tipoTrabajoActualizado
    ){
        return service.actualizar(id, tipoTrabajoActualizado);

    }

    @DeleteMapping("/tipos-trabajo/{id}")
    public boolean eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }


}
