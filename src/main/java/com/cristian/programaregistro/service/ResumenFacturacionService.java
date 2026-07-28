package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.entity.ResumenFacturacion;
import com.cristian.programaregistro.exception.ReglaNegocioException;
import com.cristian.programaregistro.repository.ClienteRepository;
import com.cristian.programaregistro.repository.ResumenFacturacionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ResumenFacturacionService {

    private final ResumenFacturacionRepository resumenFacturacionRepository;
    private final ClienteRepository clienteRepository;

    public ResumenFacturacionService(
            ResumenFacturacionRepository resumenFacturacionRepository,
            ClienteRepository clienteRepository
    ) {
        this.resumenFacturacionRepository = resumenFacturacionRepository;
        this.clienteRepository = clienteRepository;
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

        if (resumenFacturacion.getTotal() == null) {
            resumenFacturacion.setTotal(BigDecimal.ZERO);
        }

        if (resumenFacturacion.getEstadoResumen() == null || resumenFacturacion.getEstadoResumen().isBlank()) {
            resumenFacturacion.setEstadoResumen("ABIERTO");
        }

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

        resumenExistente.setFechaEmision(resumenActualizado.getFechaEmision());
        resumenExistente.setTotal(resumenActualizado.getTotal());
        resumenExistente.setEstadoResumen(resumenActualizado.getEstadoResumen());
        resumenExistente.setObservaciones(resumenActualizado.getObservaciones());
        resumenExistente.setActivo(resumenActualizado.getActivo());
        resumenExistente.setCliente(cliente);

        return Optional.of(resumenFacturacionRepository.save(resumenExistente));
    }

    public boolean eliminar(Long id) {
        return resumenFacturacionRepository.findById(id)
                .map(resumen -> {
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

    private void validarClienteActivo(Cliente cliente) {
        if (!Boolean.TRUE.equals(cliente.getActivo())) {
            throw new ReglaNegocioException("No se puede asociar un resumen a un cliente inactivo");
        }
    }
}