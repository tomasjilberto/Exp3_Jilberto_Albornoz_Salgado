package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.VentaRepository;
@Service
public class VentaServiceImpl implements  VentaService{
    @Autowired
    private VentaRepository repository; 

    @Override
    @Transactional(readOnly = true)
    public List<Venta> findByAll() {
        return (List<Venta>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findById(Long id) {
        
        return repository.findById(id);
    }

    @Override
    @Transactional 
    public Venta save(Venta unaVenta) {
        
        return repository.save(unaVenta);
    }
    @Override
    @Transactional 
    public Optional<Venta> delete(Venta unaVenta) {
        Optional<Venta> ventaOptional = repository.findById(unaVenta.getId());
        ventaOptional.ifPresent(ventaDb->{
            repository.delete(unaVenta);
        });
        return ventaOptional;
    }
    @Override
    @Transactional
    public Venta modificar(Long id, Venta ventaNueva) {
    Optional<Venta> ventaOptional = repository.findById(id);
    if (ventaOptional.isPresent()) {
        Venta ventaExistente = ventaOptional.get();

        ventaExistente.setFecha(ventaNueva.getFecha());
        ventaExistente.setTotal(ventaNueva.getTotal());

        return repository.save(ventaExistente);
    } else {
        return null; 
    }
}


}
