package com.cristian.programaregistro.service;

import java.util.List;
import java.util.Optional;


import com.cristian.programaregistro.entity.EstadoTrabajo;
import com.cristian.programaregistro.repository.EstadoTrabajoRepository;
import org.springframework.stereotype.Service;

@Service
public class EstadoTrabajoService {
    private final EstadoTrabajoRepository repository;

    public EstadoTrabajoService(EstadoTrabajoRepository repository){
        this.repository = repository;
    }

    public List<EstadoTrabajo> obtenerTodos() {
        return repository.findByActivoTrue();
    }

    public List<EstadoTrabajo> obtenerInactivos() {
        return repository.findByActivoFalse();
    }

    public EstadoTrabajo guardar(EstadoTrabajo estado){
        return repository.save(estado);
    }

    public Optional<EstadoTrabajo> obtenerPorId(Long id){
        return repository.findById(id);
    }

    public Optional<EstadoTrabajo> actualizar(Long id, EstadoTrabajo estadoActualizado) {
        return repository.findById(id)
                .map(estadoExistente -> {
                    estadoExistente.setNombre(estadoActualizado.getNombre());
                    estadoExistente.setActivo(estadoActualizado.getActivo());
                    return repository.save(estadoExistente);
                });
    }

    public boolean eliminar(Long id) {
        return repository.findById(id)
                .map(estado -> {
                    estado.setActivo(false);
                    repository.save(estado);
                    return true;
                })
                .orElse(false);
    }

    public Optional<EstadoTrabajo> reactivar(Long id) {
        return repository.findById(id)
                .map(estado -> {
                    estado.setActivo(true);
                    return repository.save(estado);
                });
    }

}
