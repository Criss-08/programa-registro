package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.DetalleResumen;
import com.cristian.programaregistro.service.DetalleResumenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-resumen")
public class DetalleResumenController {

    private final DetalleResumenService service;

    public DetalleResumenController(DetalleResumenService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleResumen> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<DetalleResumen> obtenerInactivos() {
        return service.obtenerInactivos();
    }

    @GetMapping("/resumen/{resumenFacturacionId}")
    public List<DetalleResumen> obtenerPorResumen(@PathVariable Long resumenFacturacionId) {
        return service.obtenerPorResumen(resumenFacturacionId);
    }

    @GetMapping("/trabajo/{trabajoId}")
    public List<DetalleResumen> obtenerPorTrabajo(@PathVariable Long trabajoId) {
        return service.obtenerPorTrabajo(trabajoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleResumen> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(detalleResumen -> ResponseEntity.ok(detalleResumen))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleResumen> guardar(@Valid @RequestBody DetalleResumen detalleResumen) {
        return service.guardar(detalleResumen)
                .map(detalleGuardado -> ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleResumen> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DetalleResumen detalleResumenActualizado
    ) {
        return service.actualizar(id, detalleResumenActualizado)
                .map(detalleResumen -> ResponseEntity.ok(detalleResumen))
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
    public ResponseEntity<DetalleResumen> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(detalleResumen -> ResponseEntity.ok(detalleResumen))
                .orElse(ResponseEntity.notFound().build());
    }
}