package com.cristian.programaregistro.repository;


import com.cristian.programaregistro.entity.EstadoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoTrabajoRepository
    //Implementacion automatica
    extends JpaRepository<EstadoTrabajo, Long>{

    List<EstadoTrabajo> findByActivoTrue();

    List<EstadoTrabajo> findByActivoFalse();

}
