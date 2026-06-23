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

    public List<TipoTrabajo> obtenerTodos(){
        return repository.findAll();
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
                    return repository.save(tipoTrabajoExistente);
                });
    }

    public boolean eliminar(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }



}
