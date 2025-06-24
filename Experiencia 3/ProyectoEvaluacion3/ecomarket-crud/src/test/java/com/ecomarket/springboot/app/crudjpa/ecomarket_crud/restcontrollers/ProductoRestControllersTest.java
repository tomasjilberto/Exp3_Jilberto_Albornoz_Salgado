package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.restcontrollers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ProductoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoServiceImpl productoserviceimpl;

    private List<Producto> productosLista;
    public void setUp() {
        Producto producto1 = new Producto(1L, "Cepillo", "Ecologocio", 1500);
        Producto producto2 = new Producto(2L, "Bombilla", "de Papel", 200);
        productosLista = Arrays.asList(producto1, producto2);
    }


    @Test
    public void verProductosTest() throws Exception {
        when(productoserviceimpl.findByAll()).thenReturn(productosLista);

        mockmvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void verunProductoTest(){
        Producto unProducto = new Producto(1L,"Cepillo", "Ecologocio", 1500);
        try{
            when(productoserviceimpl.findById(1L)).thenReturn(Optional.of(unProducto));
            mockmvc.perform(get("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    catch(Exception ex){
        fail("El testing lanzo un error " + ex.getMessage());
    }
    }
    @Test
    public void productoNoExisteTest() throws Exception{
        when(productoserviceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/productos/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    @Test
    public void crearProductoTest() throws Exception{
        Producto unProducto = new Producto(null,"Bombilla","de Papel",200);
        Producto otroProducto = new Producto(4L,"Calzado","bueno con el medio ambiente",40000);
        when(productoserviceimpl.save(any(Producto.class))).thenReturn(otroProducto);
        mockmvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unProducto)))
            .andExpect(status().isCreated());
    }
    @Test
    public void modificarProductoTest() throws Exception {
        Producto original = new Producto(1L, "Cepillo", "Ecologico", 1500);
        Producto modificado = new Producto(1L, "Cepillo Bamboo", "Mejorado", 2000);

        when(productoserviceimpl.findById(1L)).thenReturn(Optional.of(original));
        when(productoserviceimpl.save(any(Producto.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk());
    }
    @Test
    public void eliminarProductoTest() throws Exception {
        Producto productoAEliminar = new Producto(1L, "Bombilla", "de papel", 200);

        when(productoserviceimpl.delete(any(Producto.class))).thenReturn(Optional.of(productoAEliminar));
        mockmvc.perform(delete("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}
