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
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes")
@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {
    @Autowired
    private ClienteService service;

    
    @Operation(summary = "Obtener lista de clientes", description = "Devuelve todos los clientes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada correctamente",
                  content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Cliente.class)))
    @GetMapping
    public List<Cliente> List() {
        return service.findByAll();
    }

    @Operation(summary = "Obtener clientes por ID",description = "Obtiene el cliente especifico")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Cliente encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404",description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado correctamente",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
    @PostMapping
    public ResponseEntity<Cliente> crear (@RequestBody Cliente unCliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unCliente));
    }
    @Operation(
    summary = "Modificar un cliente",
    description = "Actualiza un cliente existente identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente modificado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Cliente unCliente){
        Optional <Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()){
            Cliente clienteexistente = clienteOptional.get();
            clienteexistente.setNombre(unCliente.getNombre());
            clienteexistente.setEdad(unCliente.getEdad());
            clienteexistente.setGmail(unCliente.getGmail());
            Cliente clientemodificado = service.save(clienteexistente);
            return ResponseEntity.ok(clientemodificado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
    summary = "Eliminar un cliente",
    description = "Elimina un cliente existente identificándolo por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Cliente unCliente = new Cliente();
        unCliente.setId(id);
        Optional<Cliente> clienteOptional = service.delete(unCliente);
        if (clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    

}




