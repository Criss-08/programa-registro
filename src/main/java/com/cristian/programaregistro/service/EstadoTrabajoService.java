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
        return repository.findAll();
    }

    public EstadoTrabajo guardar(EstadoTrabajo estado){
        return repository.save(estado);
    }

    public Optional<EstadoTrabajo> obtenerPorId(Long id){
        return repository.findById(id);
    }

    public Optional<EstadoTrabajo> actualizar(Long id, EstadoTrabajo estadoActualizado){
        return repository.findById(id)
                .map(estadoExiste -> {
                    estadoExiste.setNombre(estadoActualizado.getNombre());
                    return repository.save(estadoExiste);
                });
    }

    public boolean eliminar(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
        return false;
    }

}
