package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.dto.TrabajoDetalleResumenResponse;
import com.cristian.programaregistro.dto.TrabajoTotalResponse;
import com.cristian.programaregistro.entity.DetalleTrabajo;
import com.cristian.programaregistro.service.DetalleTrabajoService;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detalles-trabajo")
public class DetalleTrabajoController {

    private final DetalleTrabajoService service;

    public DetalleTrabajoController(DetalleTrabajoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleTrabajo> obtenerTodo() {
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<DetalleTrabajo> obtenerInactivos() {
        return service.obtenerInactivos();
    }

    @GetMapping("/trabajo/{trabajoId}")
    public List<DetalleTrabajo> obtenerPorTrabajo(@PathVariable Long trabajoId) {
        return service.obtenerPorTrabajo(trabajoId);
    }

    @GetMapping("/tipo-trabajo/{tipoTrabajoId}")
    public List<DetalleTrabajo> obtenerPorTipoTrabajo(@PathVariable Long tipoTrabajoId) {
        return service.obtenerPorTipoTrabajo(tipoTrabajoId);
    }

    @GetMapping("/trabajo/{trabajoId}/total")
    public ResponseEntity<TrabajoTotalResponse> calcularTotalPorTrabajo(@PathVariable Long trabajoId) {
        return service.calcularTotalPorTrabajo(trabajoId)
                .map(total -> ResponseEntity.ok(new TrabajoTotalResponse(trabajoId, total)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/trabajo/{trabajoId}/resumen")
    public ResponseEntity<TrabajoDetalleResumenResponse> obtenerResumenPorTrabajo(@PathVariable Long trabajoId) {
        return service.calcularTotalPorTrabajo(trabajoId)
                .flatMap(total ->
                        service.contarDetallesPorTrabajo(trabajoId)
                                .map(cantidadDetalles ->
                                        new TrabajoDetalleResumenResponse(trabajoId, cantidadDetalles, total)
                                )
                )
                .map(resumen -> ResponseEntity.ok(resumen))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleTrabajo> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(detalleTrabajo -> ResponseEntity.ok(detalleTrabajo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleTrabajo> guardar(@Valid @RequestBody DetalleTrabajo detalleTrabajo) {
        return service.guardar(detalleTrabajo)
                .map(detalleGuardado -> ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleTrabajo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DetalleTrabajo detalleTrabajoActualizado
    ) {
        return service.actualizar(id, detalleTrabajoActualizado)
                .map(detalleTrabajo -> ResponseEntity.ok(detalleTrabajo))
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
    public ResponseEntity<DetalleTrabajo> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(detalleTrabajo -> ResponseEntity.ok(detalleTrabajo))
                .orElse(ResponseEntity.notFound().build());
    }


}
