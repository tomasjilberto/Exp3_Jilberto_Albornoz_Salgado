package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("api/productos")
public class ProductoRestController {


    @Autowired
    private ProductoService service;
    @Operation(summary = "Obtener lista de productos", description = "Devuelve todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos retornada correctamente",
                  content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Producto.class)))
    @GetMapping
    public List<Producto> List() {
        return service.findByAll();
    }
    @Operation(summary = "Obtener productos por ID",description = "Obtiene el detalle de un producto especifico")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Producto encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404",description = "Producto no encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)))
    @PostMapping
    public ResponseEntity<Producto> crear (@RequestBody Producto unProducto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unProducto));
    }
    
    @Operation(
    summary = "Modificar un producto",
    description = "Actualiza un producto existente identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto modificado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
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
    
    @Operation(
    summary = "Eliminar un producto",
    description = "Elimina un producto existente identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
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
