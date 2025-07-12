package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
@Service
public interface ProductoService {
    List<Producto> findByAll();

    Optional<Producto>findById(Long id);

    Producto save (Producto unProducto);

    Optional<Producto> delete (Producto unProducto);
    Producto modificar(Long id, Producto producto);

}
