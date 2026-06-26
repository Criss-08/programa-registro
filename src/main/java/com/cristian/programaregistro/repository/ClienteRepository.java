package com.cristian.programaregistro.repository;

import com.cristian.programaregistro.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
