package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.ResumenFacturacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumenFacturacionRepository extends JpaRepository<ResumenFacturacion, Long> {

    List<ResumenFacturacion> findByActivoTrue();

    List<ResumenFacturacion> findByActivoFalse();

    List<ResumenFacturacion> findByClienteIdAndActivoTrue(Long clienteId);
}