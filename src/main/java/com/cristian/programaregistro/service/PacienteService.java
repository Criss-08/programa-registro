package com.cristian.programaregistro.service;


import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.entity.Paciente;
import com.cristian.programaregistro.repository.ClienteRepository;
import com.cristian.programaregistro.repository.PacienteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    private final ClienteRepository clienteRepository;

    public PacienteService(PacienteRepository pacienteRepository, ClienteRepository clienteRepository) {
        this.pacienteRepository = pacienteRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findByActivoTrue();
    }

    public List<Paciente> obtenerInactivo() {
        return pacienteRepository.findByActivoFalse();
    }

    public List<Paciente> obtenerPorCliente(Long clienteId) {
        return pacienteRepository.findByClienteIdAndActivoTrue(clienteId);
    }

    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> guardar(Paciente paciente) {
        if (paciente.getCliente() == null || paciente.getCliente().getId() == null) {
    return Optional.empty();
        }
        Optional<Cliente> clienteOptional = clienteRepository.findById(paciente.getCliente().getId());
        return clienteOptional.map(cliente -> {
            paciente.setCliente(cliente);
            return pacienteRepository.save(paciente);
        });

    }

    public Optional<Paciente> actualizar(Long id, Paciente pacienteActualizado) {
        if (pacienteActualizado.getCliente() == null || pacienteActualizado.getCliente().getId() == null) {
            return Optional.empty();
        }

        Optional<Cliente> clienteOptional = clienteRepository.findById(pacienteActualizado.getCliente().getId());

        return pacienteRepository.findById(id)
                .flatMap(pacienteExistente ->
                        clienteOptional.map(cliente -> {
                            pacienteExistente.setNombre(pacienteActualizado.getNombre());
                            pacienteExistente.setApellido(pacienteActualizado.getApellido());
                            pacienteExistente.setObservaciones(pacienteActualizado.getObservaciones());
                            pacienteExistente.setActivo(pacienteActualizado.getActivo());
                            pacienteExistente.setCliente(pacienteActualizado.getCliente());

                            return pacienteRepository.save(pacienteExistente);
                        })
                );
    }

    public boolean eliminar (Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setActivo(false);
                    pacienteRepository.save(paciente);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Paciente> reactivar(Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setActivo(true);
                    return pacienteRepository.save(paciente);
                });
    }


}
