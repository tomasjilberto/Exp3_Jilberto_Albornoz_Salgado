package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.ecomarket_crud.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Cliente;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.ClienteRepository;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ClienteServiceImpl;


public class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl service;
    @Mock
    private ClienteRepository repository;
    List<Cliente> list = new ArrayList<Cliente>();

    @BeforeEach
    public  void init(){
        MockitoAnnotations.openMocks(this);
        this.chargeCliente();
    }

    @Test
    public void findByAllTest(){
        when(repository.findAll()).thenReturn(list);
        List<Cliente> response  = service.findByAll();
        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }
    @Test
    public void testBuscarClientePorId() {
        Cliente clienteBuscado = list.get(0);
        when(repository.findById(1L)).thenReturn(Optional.of(clienteBuscado));

        Optional<Cliente> resultado = service.findById(1L);

        assertEquals("Tomas Jilberto", resultado.get().getNombre());
    }
    @Test
    public void testEliminarCliente() {
        Cliente clienteAEliminar = list.get(1);
        when(repository.findById(2L)).thenReturn(Optional.of(clienteAEliminar));

        Optional<Cliente> resultado = service.delete(clienteAEliminar);

        assertEquals("Juan Jara", resultado.get().getNombre());
    }

    public void chargeCliente(){
        Cliente cliente1 = new Cliente(Long.valueOf(1), "Tomas Jilberto","tomas.jilberto@gmail.com",90);
        Cliente cliente2 = new Cliente(Long.valueOf(2), "Juan Jara","juan.jara@gmail.com",40);
        Cliente cliente3 = new Cliente(Long.valueOf(3), "David Albornoz","david@gmail.com",50);
        list.add(cliente1);
        list.add(cliente2);
        list.add(cliente3);
    }
    
}
