package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
    
    @Autowired
    private ProductoRepository repository; 

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByAll() {
        return (List<Producto>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        
        return repository.findById(id);
    }

    @Override
    @Transactional 
    public Producto save(Producto unProducto) {
        
        return repository.save(unProducto);
    }
    @Override
    @Transactional 
    public Optional<Producto> delete(Producto unProducto) {
        Optional<Producto> productoOptional = repository.findById(unProducto.getId());
        productoOptional.ifPresent(productoDb->{
            repository.delete(unProducto);
        });
        return productoOptional;
    }
    @Override
    @Transactional
    public Producto modificar(Long id, Producto productoNuevo) {
    Optional<Producto> productoOptional = repository.findById(id);
    if (productoOptional.isPresent()) {
        Producto productoExistente = productoOptional.get();
        productoExistente.setNombre(productoNuevo.getNombre());
        productoExistente.setDescripcion(productoNuevo.getDescripcion());
        productoExistente.setPrecio(productoNuevo.getPrecio());
        return repository.save(productoExistente);
    } else {
        return null; 
    }
}

}
