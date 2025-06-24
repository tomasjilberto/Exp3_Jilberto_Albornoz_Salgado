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

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Venta;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.VentaRepository;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.VentaServiceImpl;

public class VentaServiceImplTest {

    @InjectMocks
    private VentaServiceImpl service;
    @Mock
    private VentaRepository repository;
    List<Venta> list = new ArrayList<Venta>();

    @BeforeEach
    public  void init(){
        MockitoAnnotations.openMocks(this);
        this.chargeVenta();
    }

    @Test
    public void findByAllTest(){
        when(repository.findAll()).thenReturn(list);
        List<Venta> response  = service.findByAll();
        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }
    @Test
    public void testBuscarVentaPorId() {
        Venta buscada = list.get(0); // 22/06/2025
        when(repository.findById(1L)).thenReturn(Optional.of(buscada));

        Optional<Venta> resultado = service.findById(1L);

        assertEquals("22/06/2025", resultado.get().getFecha());
        assertEquals(9000000, resultado.get().getTotal());
    }
    @Test
    public void testEliminarVenta() {
        Venta aEliminar = list.get(1); // 31/04/2025
        when(repository.findById(2L)).thenReturn(Optional.of(aEliminar));

        Optional<Venta> resultado = service.delete(aEliminar);

        assertEquals("31/04/2025", resultado.get().getFecha());
        assertEquals(10000, resultado.get().getTotal());
    }


    public void chargeVenta(){
        Venta venta1 = new Venta(Long.valueOf(1), "22/06/2025",9000000);
        Venta venta2 = new Venta(Long.valueOf(2), "31/04/2025",10000);
        Venta venta3 = new Venta(Long.valueOf(3), "12/06/2025",400000);
        list.add(venta1);
        list.add(venta2);
        list.add(venta3);
    }

}
