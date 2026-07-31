package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.DetalleResumen;
import com.cristian.programaregistro.entity.DetalleTrabajo;
import com.cristian.programaregistro.entity.ResumenFacturacion;
import com.cristian.programaregistro.entity.Trabajo;
import com.cristian.programaregistro.exception.ReglaNegocioException;
import com.cristian.programaregistro.repository.DetalleResumenRepository;
import com.cristian.programaregistro.repository.DetalleTrabajoRepository;
import com.cristian.programaregistro.repository.ResumenFacturacionRepository;
import com.cristian.programaregistro.repository.TrabajoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetalleResumenService {

    private final DetalleResumenRepository detalleResumenRepository;
    private final ResumenFacturacionRepository resumenFacturacionRepository;
    private final TrabajoRepository trabajoRepository;
    private final DetalleTrabajoRepository detalleTrabajoRepository;

    public DetalleResumenService(
            DetalleResumenRepository detalleResumenRepository,
            ResumenFacturacionRepository resumenFacturacionRepository,
            TrabajoRepository trabajoRepository,
            DetalleTrabajoRepository detalleTrabajoRepository
    ) {
        this.detalleResumenRepository = detalleResumenRepository;
        this.resumenFacturacionRepository = resumenFacturacionRepository;
        this.trabajoRepository = trabajoRepository;
        this.detalleTrabajoRepository = detalleTrabajoRepository;
    }

    public List<DetalleResumen> obtenerTodos() {
        return detalleResumenRepository.findByActivoTrue();
    }

    public List<DetalleResumen> obtenerInactivos() {
        return detalleResumenRepository.findByActivoFalse();
    }

    public List<DetalleResumen> obtenerPorResumen(Long resumenFacturacionId) {
        return detalleResumenRepository.findByResumenFacturacionIdAndActivoTrue(resumenFacturacionId);
    }

    public List<DetalleResumen> obtenerPorTrabajo(Long trabajoId) {
        return detalleResumenRepository.findByTrabajoIdAndActivoTrue(trabajoId);
    }

    public Optional<DetalleResumen> obtenerPorId(Long id) {
        return detalleResumenRepository.findById(id);
    }

    public Optional<DetalleResumen> guardar(DetalleResumen detalleResumen) {
        if (
                detalleResumen.getResumenFacturacion() == null ||
                        detalleResumen.getResumenFacturacion().getId() == null ||
                        detalleResumen.getTrabajo() == null ||
                        detalleResumen.getTrabajo().getId() == null
        ) {
            return Optional.empty();
        }

        Optional<ResumenFacturacion> resumenOptional =
                resumenFacturacionRepository.findById(detalleResumen.getResumenFacturacion().getId());

        Optional<Trabajo> trabajoOptional =
                trabajoRepository.findById(detalleResumen.getTrabajo().getId());

        if (resumenOptional.isEmpty() || trabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        ResumenFacturacion resumen = resumenOptional.get();
        Trabajo trabajo = trabajoOptional.get();

        validarResumenPuedeModificarse(resumen);
        validarTrabajoActivo(trabajo);
        validarTrabajoPerteneceAlClienteDelResumen(trabajo, resumen);
        validarTrabajoNoIncluidoEnOtroResumen(trabajo.getId());

        BigDecimal subtotalTrabajo = calcularSubtotalTrabajo(trabajo.getId());

        detalleResumen.setResumenFacturacion(resumen);
        detalleResumen.setTrabajo(trabajo);
        detalleResumen.setSubtotalTrabajo(subtotalTrabajo);
        detalleResumen.setActivo(true);

        DetalleResumen detalleGuardado = detalleResumenRepository.save(detalleResumen);

        recalcularTotalResumen(resumen);

        return Optional.of(detalleGuardado);
    }

    public Optional<DetalleResumen> actualizar(Long id, DetalleResumen detalleResumenActualizado) {
        Optional<DetalleResumen> detalleOptional =
                detalleResumenRepository.findById(id);

        if (detalleOptional.isEmpty()) {
            return Optional.empty();
        }

        DetalleResumen detalleExistente = detalleOptional.get();

        validarResumenPuedeModificarse(detalleExistente.getResumenFacturacion());

        BigDecimal subtotalTrabajo = calcularSubtotalTrabajo(detalleExistente.getTrabajo().getId());

        detalleExistente.setSubtotalTrabajo(subtotalTrabajo);
        detalleExistente.setObservaciones(detalleResumenActualizado.getObservaciones());


        DetalleResumen detalleGuardado = detalleResumenRepository.save(detalleExistente);

        recalcularTotalResumen(detalleExistente.getResumenFacturacion());

        return Optional.of(detalleGuardado);
    }

    public boolean eliminar(Long id) {
        return detalleResumenRepository.findById(id)
                .map(detalleResumen -> {
                    validarResumenPuedeModificarse(detalleResumen.getResumenFacturacion());

                    detalleResumen.setActivo(false);
                    detalleResumenRepository.save(detalleResumen);

                    recalcularTotalResumen(detalleResumen.getResumenFacturacion());

                    return true;
                })
                .orElse(false);
    }

    public Optional<DetalleResumen> reactivar(Long id) {
        return detalleResumenRepository.findById(id)
                .map(detalleResumen -> {
                    ResumenFacturacion resumen = detalleResumen.getResumenFacturacion();
                    Trabajo trabajo = detalleResumen.getTrabajo();

                    validarResumenPuedeModificarse(resumen);
                    validarTrabajoActivo(trabajo);
                    validarTrabajoPerteneceAlClienteDelResumen(trabajo, resumen);
                    validarTrabajoNoIncluidoEnOtroResumen(trabajo.getId());

                    BigDecimal subtotalTrabajo = calcularSubtotalTrabajo(trabajo.getId());

                    detalleResumen.setSubtotalTrabajo(subtotalTrabajo);
                    detalleResumen.setActivo(true);

                    DetalleResumen detalleGuardado = detalleResumenRepository.save(detalleResumen);

                    recalcularTotalResumen(resumen);

                    return detalleGuardado;
                });
    }

    private void validarResumenPuedeModificarse(ResumenFacturacion resumen) {
        if (!Boolean.TRUE.equals(resumen.getActivo())) {
            throw new ReglaNegocioException("No se puede modificar un resumen inactivo");
        }

        if (!"ABIERTO".equals(resumen.getEstadoResumen())) {
            throw new ReglaNegocioException("Solo se puede modificar un resumen abierto");
        }
    }

    private void validarTrabajoActivo(Trabajo trabajo) {
        if (!Boolean.TRUE.equals(trabajo.getActivo())) {
            throw new ReglaNegocioException("No se puede asociar un trabajo inactivo a un resumen");
        }
    }

    private void validarTrabajoPerteneceAlClienteDelResumen(Trabajo trabajo, ResumenFacturacion resumen) {
        if (
                trabajo.getCliente() == null ||
                        trabajo.getCliente().getId() == null ||
                        resumen.getCliente() == null ||
                        resumen.getCliente().getId() == null ||
                        !trabajo.getCliente().getId().equals(resumen.getCliente().getId())
        ) {
            throw new ReglaNegocioException("El trabajo no pertenece al cliente del resumen");
        }
    }

    private void validarTrabajoNoIncluidoEnOtroResumen(Long trabajoId) {
        if (detalleResumenRepository.existsByTrabajoIdAndActivoTrue(trabajoId)) {
            throw new ReglaNegocioException("El trabajo ya esta incluido en un resumen activo");
        }
    }

    private BigDecimal calcularSubtotalTrabajo(Long trabajoId) {
        List<DetalleTrabajo> detallesActivos =
                detalleTrabajoRepository.findByTrabajoIdAndActivoTrue(trabajoId);

        if (detallesActivos.isEmpty()) {
            throw new ReglaNegocioException("No se puede incluir un trabajo sin detalles activos en un resumen");
        }

        return detallesActivos.stream()
                .map(DetalleTrabajo::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void recalcularTotalResumen(ResumenFacturacion resumen) {
        BigDecimal total = detalleResumenRepository.findByResumenFacturacionIdAndActivoTrue(resumen.getId())
                .stream()
                .map(DetalleResumen::getSubtotalTrabajo)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        resumen.setTotal(total);
        resumenFacturacionRepository.save(resumen);
    }


}