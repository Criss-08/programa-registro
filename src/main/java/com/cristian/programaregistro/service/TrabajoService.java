package com.cristian.programaregistro.service;

import com.cristian.programaregistro.exception.ReglaNegocioException;
import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.entity.Paciente;
import com.cristian.programaregistro.entity.Trabajo;
import com.cristian.programaregistro.repository.ClienteRepository;
import com.cristian.programaregistro.repository.EstadoTrabajoRepository;
import com.cristian.programaregistro.repository.PacienteRepository;
import com.cristian.programaregistro.repository.TrabajoRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Trabajo> obtenerPorFechaEntregaEstimada(LocalDate fechaDEntregaEstimada) {
        return trabajoRepository.findByFechaEntregaEstimadaAndActivoTrue(fechaDEntregaEstimada);
    }

    public List<Trabajo> obtenerPorFechaEntregaReal(LocalDate fechaEntregaReal) {
        return trabajoRepository.findByFechaEntregaRealAndActivoTrue(fechaEntregaReal);
    }
    private void validarFechas(Trabajo trabajo) {
        if (trabajo.getFechaIngreso() == null) {
            return;
        }

        if (trabajo.getFechaEntregaEstimada() != null
                && trabajo.getFechaEntregaEstimada().isBefore(trabajo.getFechaIngreso())) {
            throw new ReglaNegocioException("La fecha de entrega estimada no puede ser anterior a la fecha de ingreso");
        }

        if (trabajo.getFechaEntregaReal() != null
                && trabajo.getFechaEntregaReal().isBefore(trabajo.getFechaIngreso())) {
            throw new ReglaNegocioException("La fecha de entrega real no puede ser anterior a la fecha de ingreso");
        }
    }

    public Optional<Trabajo> guardar(Trabajo trabajo) {
        if (trabajo.getCliente() == null || trabajo.getCliente().getId() == null ||
            trabajo.getPaciente() == null || trabajo.getPaciente().getId() == null ||
            trabajo.getEstadoTrabajo() == null || trabajo.getEstadoTrabajo().getId() == null) {
            return Optional.empty();
        }
        validarFechas(trabajo);

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(trabajo.getCliente().getId());

        Optional<Paciente> pacienteOptional =
                pacienteRepository.findById(trabajo.getPaciente().getId());

        Optional<EstadoTrabajo> estadoTrabajoOptional =
                estadoTrabajoRepository.findById(trabajo.getEstadoTrabajo().getId());

        if (clienteOptional.isEmpty() || pacienteOptional.isEmpty() || estadoTrabajoOptional.isEmpty()) {
            return Optional.empty();
        }
        Cliente cliente = clienteOptional.get();
        Paciente paciente = pacienteOptional.get();
        EstadoTrabajo estadoTrabajo = estadoTrabajoOptional.get();

        if (!pacientePerteneceAlCliente(paciente, cliente)) {
            throw new ReglaNegocioException("El paciente no pertenece al cliente indicado");
        }

        trabajo.setCliente(cliente);
        trabajo.setPaciente(paciente);
        trabajo.setEstadoTrabajo(estadoTrabajo);

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
        validarFechas(trabajoActualizado);

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

        Cliente cliente = clienteOptional.get();
        Paciente paciente = pacienteOptional.get();
        EstadoTrabajo estadoTrabajo = estadoTrabajoOptional.get();

        if (!pacientePerteneceAlCliente(paciente, cliente)) {
            throw  new ReglaNegocioException("El paciente no pertenece al cliente indicado");
        }

        Trabajo trabajoExistente = trabajoOptional.get();

        trabajoExistente.setDescripcion(trabajoActualizado.getDescripcion());
        trabajoExistente.setFechaIngreso(trabajoActualizado.getFechaIngreso());
        trabajoExistente.setFechaEntregaEstimada(trabajoActualizado.getFechaEntregaEstimada());
        trabajoExistente.setFechaEntregaReal(trabajoActualizado.getFechaEntregaReal());
        trabajoExistente.setObservaciones(trabajoActualizado.getObservaciones());
        trabajoExistente.setActivo(trabajoActualizado.getActivo());

        trabajoExistente.setCliente(cliente);
        trabajoExistente.setPaciente(paciente);
        trabajoExistente.setEstadoTrabajo(estadoTrabajo);

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

    private boolean pacientePerteneceAlCliente(Paciente paciente, Cliente cliente) {
        return paciente.getCliente() != null
                && paciente.getCliente().getId() != null
                && paciente.getCliente().getId().equals(cliente.getId());
    }

    public List<Trabajo> obtenerPorFechaIngreso(LocalDate fechaIngreso) {
        return trabajoRepository.findByFechaIngresoAndActivoTrue(fechaIngreso);
    }




}
