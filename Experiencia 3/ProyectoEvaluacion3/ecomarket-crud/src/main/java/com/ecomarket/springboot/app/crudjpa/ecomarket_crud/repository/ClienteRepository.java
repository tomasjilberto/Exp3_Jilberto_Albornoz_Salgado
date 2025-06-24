package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{

}
