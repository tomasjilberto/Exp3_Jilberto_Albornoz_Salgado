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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ClienteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteRestControllersTest {
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClienteServiceImpl clienteServiceimpl;
    private List<Cliente> clienteLista;
    public void setUp() {
        Cliente cliente1 = new Cliente(1L, "Josefina", "josefina@gmail.com", 22);
        Cliente cliente2 = new Cliente(2L, "Tomas", "tomas@gmail.com", 19);
        clienteLista = Arrays.asList(cliente1, cliente2);
    }
    @Test
    public void verClientesTest() throws Exception {
        when(clienteServiceimpl.findByAll()).thenReturn(clienteLista);

        mockmvc.perform(get("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void verunClienteTest(){
        Cliente unCliente = new Cliente(1L,"Josefina", "josefina@gmail.com", 22);
        try{
            when(clienteServiceimpl.findById(1L)).thenReturn(Optional.of(unCliente));
            mockmvc.perform(get("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    catch(Exception ex){
        fail("El testing lanzo un error " + ex.getMessage());
    }
    }
    @Test
    public void clienteNoExisteTest() throws Exception{
        when(clienteServiceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/clientes/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    public void crearClienteTest() throws Exception{
        Cliente unCliente = new Cliente(null,"Miguel","miguel@gmail.com",22);
        Cliente otroCliente = new Cliente(3L,"Rene","rene@gmail.com",18);
        when(clienteServiceimpl.save(any(Cliente.class))).thenReturn(otroCliente);
        mockmvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unCliente)))
            .andExpect(status().isCreated());
    }
    @Test
    public void modificarClienteTest() throws Exception {
        Cliente original = new Cliente(2L, "Tomas", "tomas@gmail.com", 19);
        Cliente modificado = new Cliente(2L, "Agustin", "agustin51@gmail.com", 2000);

        when(clienteServiceimpl.findById(2L)).thenReturn(Optional.of(original));
        when(clienteServiceimpl.save(any(Cliente.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/clientes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk());
    }
    @Test
    public void eliminarClienteTest() throws Exception {
        Cliente clienteAEliminar = new Cliente(2L, "Tomas", "tomas@gmail.com", 18);
        when(clienteServiceimpl.delete(any(Cliente.class))).thenReturn(Optional.of(clienteAEliminar));
        mockmvc.perform(delete("/api/clientes/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }





}
