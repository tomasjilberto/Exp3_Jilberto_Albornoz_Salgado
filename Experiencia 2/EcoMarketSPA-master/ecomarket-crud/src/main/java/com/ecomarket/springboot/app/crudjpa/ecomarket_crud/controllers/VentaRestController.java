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

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.VentaService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/ventas")
public class VentaRestController {
    @Autowired
    private VentaService service;

    @GetMapping
    public List<Venta> List() {
        return service.findByAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Venta> ventaoOptional = service.findById(id);
        if (ventaoOptional.isPresent()) {
            return ResponseEntity.ok(ventaoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Venta> crear (@RequestBody Venta unaVenta){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unaVenta));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Venta unaVenta){
        Optional <Venta> ventaoOptional = service.findById(id);
        if (ventaoOptional.isPresent()){
            Venta ventaexistente = ventaoOptional.get();
            ventaexistente.setFecha(unaVenta.getFecha());
            ventaexistente.setTotal(unaVenta.getTotal());
            Venta ventamodificada = service.save(ventaexistente);
            return ResponseEntity.ok(ventamodificada);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Venta unaVenta = new Venta();
        unaVenta.setId(id);
        Optional<Venta> ventaOptional = service.delete(unaVenta);
        if (ventaOptional.isPresent()){
            return ResponseEntity.ok(ventaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
