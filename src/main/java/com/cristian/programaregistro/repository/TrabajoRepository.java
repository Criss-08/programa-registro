package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {

    List<Trabajo> findByActivoTrue();

    List<Trabajo> findByActivoFalse();

    List<Trabajo> findByClienteIdAndActivoTrue(Long clienteId);

    List<Trabajo> findByPacienteIdAndActivoTrue(Long pacienteId);

    List<Trabajo> findByEstadoTrabajoIdAndActivoTrue(Long estadoTrabajoId);

    List<Trabajo> findByFechaIngresoAndActivoTrue(LocalDate fechaIngreso);

    List<Trabajo> findByFechaEntregaEstimadaAndActivoTrue(LocalDate fechaEntregaEstimada);

}