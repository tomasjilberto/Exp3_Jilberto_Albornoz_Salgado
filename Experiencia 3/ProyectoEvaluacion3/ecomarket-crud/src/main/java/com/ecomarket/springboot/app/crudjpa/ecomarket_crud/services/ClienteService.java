package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;
@Service
public interface ClienteService {
    List<Cliente> findByAll();
    Optional<Cliente> findById (Long id);

    Cliente save(Cliente unCliente);

    Optional<Cliente> delete (Cliente unCliente);
    Cliente modificar(Long id, Cliente cliente);

}
