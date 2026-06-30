package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    List<Cliente> findByActivoTrue();

    List<Cliente> findByActivoFalse();

}
