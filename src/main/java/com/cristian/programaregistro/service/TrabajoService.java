package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.entity.Paciente;
import com.cristian.programaregistro.entity.Trabajo;
import com.cristian.programaregistro.repository.ClienteRepository;
import com.cristian.programaregistro.repository.EstadoTrabajoRepository;
import com.cristian.programaregistro.repository.PacienteRepository;
import com.cristian.programaregistro.repository.TrabajoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;
    private final ClienteRepository clienteRepository;
    private final PacienteRepository pacienteRepository;
    private final EstadoTrabajoRepository estadoTrabajoRepository;

    public TrabajoService(
            TrabajoRepository trabajoRepository,
            ClienteRepository clienteRepository,
            PacienteRepository pacienteRepository,
            EstadoTrabajoRepository estadoTrabajoRepository
    ) {
        this.trabajoRepository = trabajoRepository;
        this.clienteRepository = clienteRepository;
        this.pacienteRepository = pacienteRepository;
        this.estadoTrabajoRepository = estadoTrabajoRepository;
    }

    public List<Trabajo> obtenerTodos() {
        return trabajoRepository.findByActivoTrue();
    }

    public List<Trabajo> obtenerInactivos() {
        return trabajoRepository.findByActivoFalse();
    }

    public List<Trabajo> obtenerPorCliente(Long clienteId) {
        return trabajoRepository.findByClienteIdAndActivoTrue(clienteId);
    }

    public List<Trabajo> obtenerPorPaciente(Long pacienteId) {
        return trabajoRepository.findByPacienteIdAndActivoTrue(pacienteId);
    }

    public List<Trabajo> obtenerPorEstado(Long estadoTrabajoId) {
        return trabajoRepository.findByEstadoTrabajoIdAndActivoTrue(estadoTrabajoId);
    }

    public Optional<Trabajo> obtenerPorId(Long id) {
        return trabajoRepository.findById(id);
    }

    public Optional<Trabajo> guardar(Trabajo trabajo) {
        if (trabajo.getCliente() == null || trabajo.getCliente().getId() == null ||
            trabajo.getPaciente() == null || trabajo.getPaciente().getId() == null ||
            trabajo.getEstadoTrabajo() == null || trabajo.getEstadoTrabajo().getId() == null) {
            return Optional.empty();
        }

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(trabajo.getCliente().getId());

        Optional<Paciente> pacienteOptional =
                pacienteRepository.findById(trabajo.getPaciente().getId());

        Optional<EstadoTrabajo> estadoTrabajoOptional =
                estadoTrabajoRepository.findById(trabajo.getEstadoTrabajo().getId());

        if (clienteOptional.isEmpty() || pacienteOptional.isEmpty() || estadoTrabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        trabajo.setCliente(clienteOptional.get());
        trabajo.setPaciente(pacienteOptional.get());
        trabajo.setEstadoTrabajo(estadoTrabajoOptional.get());

        return Optional.of(trabajoRepository.save(trabajo));
    }

    public Optional<Trabajo> actualizar(Long id, Trabajo trabajoActualizado) {
        if (
                trabajoActualizado.getCliente() == null || trabajoActualizado.getCliente().getId() == null ||
                        trabajoActualizado.getPaciente() == null || trabajoActualizado.getPaciente().getId() == null ||
                        trabajoActualizado.getEstadoTrabajo() == null || trabajoActualizado.getEstadoTrabajo().getId() == null
        ) {
            return Optional.empty();
        }

        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
        Optional<Cliente> clienteOptional =
                clienteRepository.findById(trabajoActualizado.getCliente().getId());
        Optional<Paciente> pacienteOptional =
                pacienteRepository.findById(trabajoActualizado.getPaciente().getId());
        Optional<EstadoTrabajo> estadoTrabajoOptional =
                estadoTrabajoRepository.findById(trabajoActualizado.getEstadoTrabajo().getId());

        if (trabajoOptional.isEmpty() || clienteOptional.isEmpty()
                || pacienteOptional.isEmpty() || estadoTrabajoOptional.isEmpty()) {
            return Optional.empty();
        }

        Trabajo trabajoExistente = trabajoOptional.get();

        trabajoExistente.setDescripcion(trabajoActualizado.getDescripcion());
        trabajoExistente.setFechaIngreso(trabajoActualizado.getFechaIngreso());
        trabajoExistente.setFechaEntregaEstimada(trabajoActualizado.getFechaEntregaEstimada());
        trabajoExistente.setFechaEntregaReal(trabajoActualizado.getFechaEntregaReal());
        trabajoExistente.setObservaciones(trabajoActualizado.getObservaciones());
        trabajoExistente.setActivo(trabajoActualizado.getActivo());

        trabajoExistente.setCliente(clienteOptional.get());
        trabajoExistente.setPaciente(pacienteOptional.get());
        trabajoExistente.setEstadoTrabajo(estadoTrabajoOptional.get());

        return Optional.of(trabajoRepository.save(trabajoExistente));
    }

    public boolean eliminar(Long id) {
        return trabajoRepository.findById(id)
                .map(trabajo -> {
                    trabajo.setActivo(false);
                    trabajoRepository.save(trabajo);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Trabajo> reactivar(Long id) {
        return trabajoRepository.findById(id)
                .map(trabajo -> {
                    trabajo.setActivo(true);
                    return trabajoRepository.save(trabajo);
                });
    }





}
