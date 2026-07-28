package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.TipoTrabajo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TipoTrabajoRepository  extends JpaRepository<TipoTrabajo, Long>{

    List<TipoTrabajo> findByActivoTrue();

    List<TipoTrabajo> findByActivoFalse();

}
