package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.DetalleTrabajo;
import com.cristian.programaregistro.entity.TipoTrabajo;
import com.cristian.programaregistro.entity.Trabajo;
import com.cristian.programaregistro.exception.ReglaNegocioException;
import com.cristian.programaregistro.repository.DetalleTrabajoRepository;
import com.cristian.programaregistro.repository.TipoTrabajoRepository;
import com.cristian.programaregistro.repository.TrabajoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleTrabajoService {

    private final DetalleTrabajoRepository detalleTrabajoRepository;
    private final TrabajoRepository trabajoRepository;
    private final TipoTrabajoRepository tipoTrabajoRepository;

    public DetalleTrabajoService(
            DetalleTrabajoRepository detalleTrabajoRepository,
            TrabajoRepository trabajoRepository,
            TipoTrabajoRepository tipoTrabajoRepository
    ) {
        this.detalleTrabajoRepository = detalleTrabajoRepository;
        this.trabajoRepository = trabajoRepository;
        this.tipoTrabajoRepository = tipoTrabajoRepository;
    }

    public List<DetalleTrabajo> obtenerTodos() {
        return detalleTrabajoRepository.findByActivoTrue();
    }

    public List<DetalleTrabajo> obtenerInactivos() {
        return detalleTrabajoRepository.findByActivoFalse();
    }

    public List<DetalleTrabajo> obtenerPorTrabajo(Long trabajoId) {
        return detalleTrabajoRepository.findByTrabajoIdAndActivoTrue(trabajoId);
    }

    public List<DetalleTrabajo> obtenerPorTipoTrabajo(Long tipoTrabajoId) {
        return detalleTrabajoRepository.findByTipoTrabajoIdAndActivoTrue(tipoTrabajoId);
    }

    public Optional<DetalleTrabajo> obtenerPorId(Long id) {
        return detalleTrabajoRepository.findById(id);
    }

    public Optional<DetalleTrabajo> guardar(DetalleTrabajo detalleTrabajo) {
        if (
                detalleTrabajo.getTrabajo() == null || detalleTrabajo.getTrabajo().getId() == null ||
                        detalleTrabajo.getTipoTrabajo() == null || detalleTrabajo.getTipoTrabajo().getId() == null
        ) {
            return Optional.empty();
        }

        Optional<Trabajo> trabajoOptional =
                trabajoRepository.findById(detalleTrabajo.getTrabajo().getId());

        Optional<TipoTrabajo> tipoTrabajoOptional =
                tipoTrabajoRepository.findById(detalleTrabajo.getTipoTrabajo().getId());

        if (trabajoOptional.isEmpty() || tipoTrabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        Trabajo trabajo = trabajoOptional.get();
        TipoTrabajo tipoTrabajo = tipoTrabajoOptional.get();

        validarTrabajoActivo(trabajo);
        calcularSubtotal(detalleTrabajo);

        detalleTrabajo.setTrabajo(trabajo);
        detalleTrabajo.setTipoTrabajo(tipoTrabajo);

        return Optional.of(detalleTrabajoRepository.save(detalleTrabajo));
    }

    public Optional<DetalleTrabajo> actualizar(Long id, DetalleTrabajo detalleTrabajoActualizado) {
        if (
                detalleTrabajoActualizado.getTrabajo() == null || detalleTrabajoActualizado.getTrabajo().getId() == null ||
                        detalleTrabajoActualizado.getTipoTrabajo() == null || detalleTrabajoActualizado.getTipoTrabajo().getId() == null
        ) {
            return Optional.empty();
        }

        Optional<DetalleTrabajo> detalleTrabajoOptional =
                detalleTrabajoRepository.findById(id);

        Optional<Trabajo> trabajoOptional =
                trabajoRepository.findById(detalleTrabajoActualizado.getTrabajo().getId());

        Optional<TipoTrabajo> tipoTrabajoOptional =
                tipoTrabajoRepository.findById(detalleTrabajoActualizado.getTipoTrabajo().getId());

        if (detalleTrabajoOptional.isEmpty() || trabajoOptional.isEmpty() || tipoTrabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        Trabajo trabajo = trabajoOptional.get();
        TipoTrabajo tipoTrabajo = tipoTrabajoOptional.get();

        validarTrabajoActivo(trabajo);

        DetalleTrabajo detalleTrabajoExistente = detalleTrabajoOptional.get();

        detalleTrabajoExistente.setCantidad(detalleTrabajoActualizado.getCantidad());
        detalleTrabajoExistente.setPrecioUnitario(detalleTrabajoActualizado.getPrecioUnitario());
        detalleTrabajoExistente.setObservaciones(detalleTrabajoActualizado.getObservaciones());
        detalleTrabajoExistente.setActivo(detalleTrabajoActualizado.getActivo());

        detalleTrabajoExistente.setTrabajo(trabajo);
        detalleTrabajoExistente.setTipoTrabajo(tipoTrabajo);

        calcularSubtotal(detalleTrabajoExistente);

        return Optional.of(detalleTrabajoRepository.save(detalleTrabajoExistente));
    }

    public boolean eliminar(Long id) {
        return detalleTrabajoRepository.findById(id)
                .map(detalleTrabajo -> {
                    detalleTrabajo.setActivo(false);
                    detalleTrabajoRepository.save(detalleTrabajo);
                    return true;
                })
                .orElse(false);
    }

    public Optional<DetalleTrabajo> reactivar(Long id) {
        return detalleTrabajoRepository.findById(id)
                .map(detalleTrabajo -> {
                    detalleTrabajo.setActivo(true);
                    return detalleTrabajoRepository.save(detalleTrabajo);
                });
    }

    public Optional<BigDecimal> calcularTotalPorTrabajo(Long trabajoId) {
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(trabajoId);

        if (trabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal total = detalleTrabajoRepository.findByTrabajoIdAndActivoTrue(trabajoId)
                .stream()
                .map(DetalleTrabajo::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Optional.of(total);
    }


    private void validarTrabajoActivo(Trabajo trabajo) {
        if (!Boolean.TRUE.equals(trabajo.getActivo())) {
            throw new ReglaNegocioException("No se puede asociar un detalle a un trabajo inactivo");
        }
    }

    private void calcularSubtotal(DetalleTrabajo detalleTrabajo) {
        BigDecimal subtotal = detalleTrabajo.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalleTrabajo.getCantidad()));

        detalleTrabajo.setSubtotal(subtotal);
    }

}
