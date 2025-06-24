package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ClienteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {
    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> List() {
        return service.findByAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Cliente> crear (@RequestBody Cliente unCliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unCliente));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Cliente unCliente){
        Optional <Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()){
            Cliente clienteexistente = clienteOptional.get();
            clienteexistente.setNombre(unCliente.getNombre());
            clienteexistente.setEdad(unCliente.getEdad());
            clienteexistente.setGmail(unCliente.getGmail());
            Cliente clientemodificado = service.save(clienteexistente);
            return ResponseEntity.ok(clientemodificado);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Cliente unCliente = new Cliente();
        unCliente.setId(id);
        Optional<Cliente> clienteOptional = service.delete(unCliente);
        if (clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    

}




