package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    List<Paciente> findByActivoTrue();

    List<Paciente> findByActivoFalse();

    List<Paciente> findByClienteIdAndActivoTrue(Long clienteId);

}
