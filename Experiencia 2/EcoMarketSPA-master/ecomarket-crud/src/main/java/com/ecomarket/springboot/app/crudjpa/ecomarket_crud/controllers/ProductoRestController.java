package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.ProductoRepository;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ProductoService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/productos")
public class ProductoRestController {

    private final ProductoRepository productoRepository;
    @Autowired
    private ProductoService service;

    ProductoRestController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    @GetMapping
    public List<Producto> List() {
        return service.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping
    public ResponseEntity<Producto> crear (@RequestBody Producto unProducto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Producto unProducto){
        Optional <Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()){
            Producto productoexistente = productoOptional.get();
            productoexistente.setNombre(unProducto.getNombre());
            productoexistente.setDescripcion(unProducto.getDescripcion());
            productoexistente.setPrecio(unProducto.getPrecio());
            Producto productomodificado = service.save(productoexistente);
            return ResponseEntity.ok(productomodificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Producto unProducto = new Producto();
        unProducto.setId(id);
        Optional<Producto> productoOptional = service.delete(unProducto);
        if (productoOptional.isPresent()){
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    

}
