package com.cristian.programaregistro.service;

import java.util.List;

import com.cristian.programaregistro.controller.EstadoTrabajoController;
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



}
