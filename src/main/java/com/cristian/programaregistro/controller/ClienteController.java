package com.cristian.programaregistro.controller;

import com.cristian.programaregistro.entity.Cliente;
import com.cristian.programaregistro.service.ClienteService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
      this.service = service;
    }

    @GetMapping
    public List<Cliente> obenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/inactivos")
    public List<Cliente> obtenerInactivos() {
        return service.obtenerInactivo();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
        .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody Cliente cliente) {
        Cliente clienteGuardado = service.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar (
            @PathVariable Long id,
            @Valid @RequestBody Cliente clienteActualizado
    ) {
        return service.actulizar(id, clienteActualizado)
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reactivar")
    public ResponseEntity<Cliente> reactivar(@PathVariable Long id) {
        return service.reactivar(id)
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
