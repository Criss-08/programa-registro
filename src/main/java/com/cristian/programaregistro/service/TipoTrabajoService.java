package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.TipoTrabajo;
import com.cristian.programaregistro.repository.TipoTrabajoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoTrabajoService {
    private final TipoTrabajoRepository repository;

    public TipoTrabajoService(TipoTrabajoRepository repository){
        this.repository = repository;
    }

    public List<TipoTrabajo> obtenerTodos() {
        return repository.findByActivoTrue();
    }

    public List<TipoTrabajo> obtenerInactivos() {
        return repository.findByActivoFalse();
    }

    public Optional<TipoTrabajo> obtenerPorId(Long id){
        return repository.findById(id);
    }

    public TipoTrabajo guardar(TipoTrabajo tipoTrabajo){
        return repository.save(tipoTrabajo);
    }

    public Optional<TipoTrabajo> actualizar(Long id, TipoTrabajo tipoTrabajoActualizado) {
        return repository.findById(id)
                .map(tipoTrabajoExistente -> {
                    tipoTrabajoExistente.setNombre(tipoTrabajoActualizado.getNombre());
                    tipoTrabajoExistente.setPrecioBase(tipoTrabajoActualizado.getPrecioBase());
                    tipoTrabajoExistente.setActivo(tipoTrabajoActualizado.getActivo());
                    return repository.save(tipoTrabajoExistente);
                });
    }

    public boolean eliminar(Long id) {
        return repository.findById(id)
                .map(tipoTrabajo -> {
                    tipoTrabajo.setActivo(false);
                    repository.save(tipoTrabajo);
                    return true;
                })
                .orElse(false);
    }

    public Optional<TipoTrabajo> reactivar(Long id) {
        return repository.findById(id)
                .map(tipoTrabajo -> {
                    tipoTrabajo.setActivo(true);
                    return repository.save(tipoTrabajo);
                });
    }



}
