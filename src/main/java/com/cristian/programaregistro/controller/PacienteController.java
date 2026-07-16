package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.Paciente;
import com.cristian.programaregistro.service.PacienteService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service){
        this.service = service;
    }

    @GetMapping
    public List<Paciente> obtenerTodos(){
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<Paciente> obtenerInactivos(){
       return service.obtenerInactivo();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Paciente> obtenerPorCliente(@PathVariable Long clienteId){
        return service.obtenerPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(paciente -> ResponseEntity.ok(paciente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@Valid @RequestBody Paciente paciente) {
        return service.guardar(paciente)
                .map(pacienteGuardado -> ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar (
            @PathVariable Long id,
            @Valid @RequestBody Paciente pacinenteActualizado
    ){
        return service.actualizar(id, pacinenteActualizado)
                .map(paciente -> ResponseEntity.ok(paciente))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = service.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/reactivar")
    public ResponseEntity<Paciente> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(paciente -> ResponseEntity.ok(paciente))
                .orElse(ResponseEntity.notFound().build());
    }




}
