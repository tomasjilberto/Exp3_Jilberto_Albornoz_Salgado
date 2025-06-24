package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.VentaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
@SpringBootTest
@AutoConfigureMockMvc
public class VentaRestControllersTest {
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VentaServiceImpl ventaServiceImpl;
    private List<Venta> ventasLista;

    public void setUp() {
        Venta venta1 = new Venta(1L, "21/08/23", 22332);
        Venta venta2 = new Venta(2L, "29/01/24", 19990);
        ventasLista = Arrays.asList(venta1, venta2);
    }
    @Test
    public void verVentaTest() throws Exception {
        when(ventaServiceImpl.findByAll()).thenReturn(ventasLista);

        mockmvc.perform(get("/api/ventas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void verunaVentaTest(){
        Venta unaVenta = new Venta(1L,"21/08/23", 22332);
        try{
            when(ventaServiceImpl.findById(1L)).thenReturn(Optional.of(unaVenta));
            mockmvc.perform(get("/api/ventas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    catch(Exception ex){
        fail("El testing lanzo un error " + ex.getMessage());
    }
    }
    @Test
    public void ventaNoExisteTest() throws Exception{
        when(ventaServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/ventas/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    @Test
    public void crearVentaTest() throws Exception{
        Venta unaVenta = new Venta(6L,"23/03/2011",600000);
        Venta otraVenta = new Venta(5L,"21/03/2021",40000);
        when(ventaServiceImpl.save(any(Venta.class))).thenReturn(otraVenta);
        mockmvc.perform(post("/api/ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unaVenta)))
            .andExpect(status().isCreated());
    }
    @Test
    public void modificarVentaTest() throws Exception {
        Venta original = new Venta(1L,"21/08/23", 22332);
        Venta modificado = new Venta(1L, "21/08/23",  1999900);

        when(ventaServiceImpl.findById(1L)).thenReturn(Optional.of(original));
        when(ventaServiceImpl.save(any(Venta.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/ventas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk());
    }
    @Test
    public void eliminarVentaTest() throws Exception {
        Venta ventaAEliminar = new Venta(1L, "21/08/23",  1999900);

        when(ventaServiceImpl.delete(any(Venta.class))).thenReturn(Optional.of(ventaAEliminar));
        mockmvc.perform(delete("/api/ventas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }




}
