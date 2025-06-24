package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
@Service
public interface VentaService {
    List<Venta> findByAll();

    Optional<Venta>findById(Long id);

    Venta save (Venta unaVenta);

    Optional<Venta> delete (Venta unaVenta);

}
