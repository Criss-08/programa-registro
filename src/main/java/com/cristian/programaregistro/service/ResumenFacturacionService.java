package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.entity.ResumenFacturacion;
import com.cristian.programaregistro.exception.ReglaNegocioException;
import com.cristian.programaregistro.repository.ClienteRepository;
import com.cristian.programaregistro.repository.ResumenFacturacionRepository;
import org.springframework.stereotype.Service;
import com.cristian.programaregistro.repository.DetalleResumenRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ResumenFacturacionService {

    private final ResumenFacturacionRepository resumenFacturacionRepository;
    private final ClienteRepository clienteRepository;
    private final DetalleResumenRepository detalleResumenRepository;

    public ResumenFacturacionService(
            ResumenFacturacionRepository resumenFacturacionRepository,
            ClienteRepository clienteRepository,
            DetalleResumenRepository detalleResumenRepository
    ) {
        this.resumenFacturacionRepository = resumenFacturacionRepository;
        this.clienteRepository = clienteRepository;

        this.detalleResumenRepository = detalleResumenRepository;
    }

    public List<ResumenFacturacion> obtenerTodos() {
        return resumenFacturacionRepository.findByActivoTrue();
    }

    public List<ResumenFacturacion> obtenerInactivos() {
        return resumenFacturacionRepository.findByActivoFalse();
    }

    public List<ResumenFacturacion> obtenerPorCliente(Long clienteId) {
        return resumenFacturacionRepository.findByClienteIdAndActivoTrue(clienteId);
    }

    public Optional<ResumenFacturacion> obtenerPorId(Long id) {
        return resumenFacturacionRepository.findById(id);
    }

    public Optional<ResumenFacturacion> guardar(ResumenFacturacion resumenFacturacion) {
        if (resumenFacturacion.getCliente() == null || resumenFacturacion.getCliente().getId() == null) {
            return Optional.empty();
        }

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(resumenFacturacion.getCliente().getId());

        if (clienteOptional.isEmpty()) {
            return Optional.empty();
        }

        Cliente cliente = clienteOptional.get();

        validarClienteActivo(cliente);

        resumenFacturacion.setCliente(cliente);

        resumenFacturacion.setTotal(BigDecimal.ZERO);

        if (resumenFacturacion.getEstadoResumen() == null || resumenFacturacion.getEstadoResumen().isBlank()) {
            resumenFacturacion.setEstadoResumen("ABIERTO");
        }
        validarEstadoResumen(resumenFacturacion.getEstadoResumen());

        return Optional.of(resumenFacturacionRepository.save(resumenFacturacion));
    }

    public Optional<ResumenFacturacion> actualizar(Long id, ResumenFacturacion resumenActualizado) {
        if (resumenActualizado.getCliente() == null || resumenActualizado.getCliente().getId() == null) {
            return Optional.empty();
        }

        Optional<ResumenFacturacion> resumenOptional =
                resumenFacturacionRepository.findById(id);

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(resumenActualizado.getCliente().getId());

        if (resumenOptional.isEmpty() || clienteOptional.isEmpty()) {
            return Optional.empty();
        }

        Cliente cliente = clienteOptional.get();

        validarClienteActivo(cliente);

        ResumenFacturacion resumenExistente = resumenOptional.get();

        validarCambioDeCliente(resumenExistente, cliente);
        validarEstadoResumen(resumenActualizado.getEstadoResumen());


        resumenExistente.setFechaEmision(resumenActualizado.getFechaEmision());
        resumenExistente.setEstadoResumen(resumenActualizado.getEstadoResumen());
        resumenExistente.setObservaciones(resumenActualizado.getObservaciones());
        resumenExistente.setCliente(cliente);

        return Optional.of(resumenFacturacionRepository.save(resumenExistente));
    }

    public boolean eliminar(Long id) {
        return resumenFacturacionRepository.findById(id)
                .map(resumen -> {
                    validarResumenSinDetallesActivos(resumen);

                    resumen.setActivo(false);
                    resumenFacturacionRepository.save(resumen);
                    return true;
                })
                .orElse(false);
    }

    public Optional<ResumenFacturacion> reactivar(Long id) {
        return resumenFacturacionRepository.findById(id)
                .map(resumen -> {
                    resumen.setActivo(true);
                    return resumenFacturacionRepository.save(resumen);
                });
    }

    public Optional<ResumenFacturacion> cerrar(Long id) {
        return resumenFacturacionRepository.findById(id)
                .map(resumen -> {
                    validarResumenActivo(resumen);

                    if (!"ABIERTO".equals(resumen.getEstadoResumen())) {
                        throw new ReglaNegocioException("Solo se puede cerrar un resumen abierto");
                    }

                    resumen.setEstadoResumen("CERRADO");

                    return resumenFacturacionRepository.save(resumen);
                });
    }

    private void validarClienteActivo(Cliente cliente) {
        if (!Boolean.TRUE.equals(cliente.getActivo())) {
            throw new ReglaNegocioException("No se puede asociar un resumen a un cliente inactivo");
        }
    }

    private void validarEstadoResumen(String estadoResumen) {
        if (
                !estadoResumen.equals("ABIERTO") &&
                        !estadoResumen.equals("CONFIRMADO") &&
                        !estadoResumen.equals("CERRADO") &&
                        !estadoResumen.equals("ANULADO")
        ) {
            throw new ReglaNegocioException("El estado del resumen no es valido");
        }
    }

    private void validarCambioDeCliente(ResumenFacturacion resumenExistente, Cliente clienteNuevo) {
        boolean tieneDetallesActivos =
                detalleResumenRepository.existsByResumenFacturacionIdAndActivoTrue(resumenExistente.getId());

        boolean cambioCliente =
                !resumenExistente.getCliente().getId().equals(clienteNuevo.getId());

        if (tieneDetallesActivos && cambioCliente) {
            throw new ReglaNegocioException("No se puede cambiar el cliente de un resumen con trabajos incluidos");
        }
    }

    private void validarResumenSinDetallesActivos(ResumenFacturacion resumen) {
        boolean tieneDetallesActivos =
                detalleResumenRepository.existsByResumenFacturacionIdAndActivoTrue(resumen.getId());

        if (tieneDetallesActivos) {
            throw new ReglaNegocioException("No se puede eliminar un resumen con trabajos incluidos");
        }
    }

    private void validarResumenActivo(ResumenFacturacion resumen) {
        if (!Boolean.TRUE.equals(resumen.getActivo())) {
            throw new ReglaNegocioException("No se puede modificar un resumen inactivo");
        }
    }





}