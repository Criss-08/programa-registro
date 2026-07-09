package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.Trabajo;
import com.cristian.programaregistro.service.TrabajoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trabajos")
public class TrabajoController {

    private final TrabajoService service;

    public TrabajoController(TrabajoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trabajo> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<Trabajo> obtenerInactivos() {
        return service.obtenerInactivos();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Trabajo> obtenerPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Trabajo> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return service.obtenerPorPaciente(pacienteId);
    }

    @GetMapping("/estado/{estadoTrabajoId}")
    public List<Trabajo> obtenerPorEstado(@PathVariable Long estadoTrabajoId) {
        return service.obtenerPorEstado(estadoTrabajoId);
    }

    @GetMapping("/fecha-entrega-real/{fechaEntregaReal}")
    public List<Trabajo> obtenerPorFechaEntregaReal(@PathVariable LocalDate fechaEntregaReal) {
        return service.obtenerPorFechaEntregaReal(fechaEntregaReal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trabajo> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(trabajo -> ResponseEntity.ok(trabajo))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fecha-ingreso/{fechaIngreso}")
    public List<Trabajo> obtenerPorFechaIngreso(@PathVariable LocalDate fechaIngreso) {
        return service.obtenerPorFechaIngreso(fechaIngreso);
    }

    @GetMapping("/fecha-entrega-estimada/{fechaEntregaEstimada}")
    public List<Trabajo> obtenePorFechaEntregaEstimada(@PathVariable LocalDate fechaEntregaEstimada) {
        return service.obtenerPorFechaEntregaEstimada(fechaEntregaEstimada);
    }

    @PostMapping
    public ResponseEntity<Trabajo> guardar(@Valid @RequestBody Trabajo trabajo) {
        return service.guardar(trabajo)
                .map(trabajoGuardado -> ResponseEntity.status(HttpStatus.CREATED).body(trabajoGuardado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
     public ResponseEntity<Trabajo> actualizar(
             @PathVariable Long id,
             @Valid @RequestBody Trabajo trabajoActualizado) {
        return service.actualizar(id, trabajoActualizado)
                .map(trabajo -> ResponseEntity.ok(trabajo))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reactivar")
    public ResponseEntity<Trabajo> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(trabajo -> ResponseEntity.ok(trabajo))
                .orElse(ResponseEntity.notFound().build());
    }
}

