package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.DetalleResumen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleResumenRepository extends JpaRepository<DetalleResumen, Long> {

    List<DetalleResumen> findByActivoTrue();

    List<DetalleResumen> findByActivoFalse();

    List<DetalleResumen> findByResumenFacturacionIdAndActivoTrue(Long resumenFacturacionId);

    List<DetalleResumen> findByTrabajoIdAndActivoTrue(Long trabajoId);

    boolean existsByTrabajoIdAndActivoTrue(Long trabajoId);

    boolean existsByResumenFacturacionIdAndActivoTrue(Long resumenFacturacionId);

}