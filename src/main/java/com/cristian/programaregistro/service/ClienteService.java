package com.cristian.programaregistro.service;

import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.repository.ClienteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

public ClienteService(ClienteRepository repository){
    this.repository = repository;
}


public List<Cliente> obtenerTodos(){
    return repository.findAll();
}

public Optional<Cliente> obtenerPorId(Long id) {
    return repository.findById(id);
}


public Cliente guardar(Cliente cliente){
    return repository.save(cliente);
}

public Optional<Cliente> actulizar (Long id, Cliente clienteActualizado){
    return repository.findById(id)
            .map(clienteExistente -> {
                clienteExistente.setNombre(clienteActualizado.getNombre());
                clienteExistente.setEmail(clienteActualizado.getEmail());
                clienteExistente.setTelefono(clienteActualizado.getTelefono());
                clienteExistente.setDireccion(clienteActualizado.getDireccion());
                clienteExistente.setObservaciones(clienteActualizado.getObservaciones());
                clienteExistente.setActivo(clienteActualizado.getActivo());

                return repository.save(clienteExistente);
            });


}



}
