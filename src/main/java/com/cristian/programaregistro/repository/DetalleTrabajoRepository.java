package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.DetalleTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleTrabajoRepository extends JpaRepository<DetalleTrabajo, Long> {

    List<DetalleTrabajo> findByActivoTrue();

    List<DetalleTrabajo> findByActivoFalse();

    List<DetalleTrabajo> findByTrabajoIdAndActivoTrue(Long trabajoId);

    List<DetalleTrabajo> findByTipoTrabajoIdAndActivoTrue(Long tipoTrabajoId);



}
