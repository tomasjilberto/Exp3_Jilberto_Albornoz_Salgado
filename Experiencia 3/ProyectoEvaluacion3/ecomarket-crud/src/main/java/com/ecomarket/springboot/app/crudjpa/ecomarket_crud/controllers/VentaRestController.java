package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Ventas", description = "Operaciones relacionadas con Ventas")
@RestController
@RequestMapping("api/ventas")
public class VentaRestController {
    @Autowired
    private VentaService service;

    @Operation(summary = "Obtener lista de Ventas", description = "Devuelve todos las ventas disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de ventas retornada correctamente",
                  content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Venta.class)))
    @GetMapping
    public List<Venta> List() {
        return service.findByAll();
    }
    @Operation(summary = "Obtener Ventas por ID",description = "Obtiene la venta especifica")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Venta encontrada",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
        @ApiResponse(responseCode = "404",description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Venta> ventaoOptional = service.findById(id);
        if (ventaoOptional.isPresent()) {
            return ResponseEntity.ok(ventaoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Crear una nueva Venta", description = "Crea una nueva Venta")
    @ApiResponse(responseCode = "201", description = "Venta creada correctamente",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class)))
    @PostMapping
    public ResponseEntity<Venta> crear (@RequestBody Venta unaVenta){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unaVenta));
    }
    @Operation(
    summary = "Modificar una venta",
    description = "Actualiza una venta existente identificándola por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta modificada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
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
    @Operation(
    summary = "Eliminar una Venta",
    description = "Elimina una Venta existente identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
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
