package com.cristian.programaregistro.repository;


import com.cristian.programaregistro.entity.EstadoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstadoTrabajoRepository
    //Implementacion automatica
    extends JpaRepository<EstadoTrabajo, Long>{


}
