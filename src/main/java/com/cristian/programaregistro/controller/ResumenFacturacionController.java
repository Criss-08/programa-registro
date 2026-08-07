package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.ResumenFacturacion;
import com.cristian.programaregistro.service.ResumenFacturacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumenes-facturacion")
public class ResumenFacturacionController {

    private final ResumenFacturacionService service;

    public ResumenFacturacionController(ResumenFacturacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ResumenFacturacion> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<ResumenFacturacion> obtenerInactivos() {
        return service.obtenerInactivos();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<ResumenFacturacion> obtenerPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumenFacturacion> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(resumen -> ResponseEntity.ok(resumen))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResumenFacturacion> guardar(@Valid @RequestBody ResumenFacturacion resumenFacturacion) {
        return service.guardar(resumenFacturacion)
                .map(resumenGuardado -> ResponseEntity.status(HttpStatus.CREATED).body(resumenGuardado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumenFacturacion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResumenFacturacion resumenActualizado
    ) {
        return service.actualizar(id, resumenActualizado)
                .map(resumen -> ResponseEntity.ok(resumen))
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
    public ResponseEntity<ResumenFacturacion> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(resumen -> ResponseEntity.ok(resumen))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<ResumenFacturacion> cerrar(@PathVariable Long id) {
        return service.cerrar(id)
                .map(resumen -> ResponseEntity.ok(resumen))
                .orElse(ResponseEntity.notFound().build());
    }


}