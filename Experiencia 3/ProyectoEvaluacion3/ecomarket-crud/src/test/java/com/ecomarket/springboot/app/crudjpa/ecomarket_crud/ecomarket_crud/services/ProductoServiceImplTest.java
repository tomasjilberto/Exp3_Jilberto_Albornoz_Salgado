package com.ecomarket.springboot.app.crudjpa.ecomarket_crud.ecomarket_crud.services;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.entities.Producto;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.repository.ProductoRepository;
import com.ecomarket.springboot.app.crudjpa.ecomarket_crud.services.ProductoServiceImpl;

public class ProductoServiceImplTest {

    @InjectMocks
    private ProductoServiceImpl service;
    @Mock
    private ProductoRepository repository;
    List<Producto> list = new ArrayList<Producto>();

    @BeforeEach
    public  void init(){
        MockitoAnnotations.openMocks(this);
        this.chargeProducto();
    }
    ///METODO PARA ENCONTRAR ALL TEST
    @Test
    public void findByAllTest(){
        when(repository.findAll()).thenReturn(list);
        List<Producto> response  = service.findByAll();
        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }
    @Test
    public void testBuscarProductoPorId() {
        Producto buscado = list.get(0); // Miel Organica
        when(repository.findById(1L)).thenReturn(Optional.of(buscado));

        Optional<Producto> resultado = service.findById(1L);

        assertEquals("Miel Organica", resultado.get().getNombre());
        assertEquals("dulce", resultado.get().getDescripcion());
        assertEquals(6000, resultado.get().getPrecio());
    }
    @Test
    public void testEliminarProducto() {
        Producto aEliminar = list.get(2); // Cepillo de Dientes
        when(repository.findById(3L)).thenReturn(Optional.of(aEliminar));

        Optional<Producto> resultado = service.delete(aEliminar);

        assertEquals("Cepillo de Dientes", resultado.get().getNombre());
        assertEquals("de Bambú", resultado.get().getDescripcion());
        assertEquals(4000, resultado.get().getPrecio());
    }
    @Test
    public void testModificarProducto() {
        Producto productoExistente = list.get(0); // Miel Organica

        Producto productoModificado = new Producto();
        productoModificado.setId(1L);
        productoModificado.setNombre("Miel Premium");
        productoModificado.setDescripcion("muy dulce");
        productoModificado.setPrecio(7000);

        when(repository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(repository.save(productoExistente)).thenReturn(productoExistente);

        Producto resultado = service.modificar(1L, productoModificado);

        assertEquals("Miel Premium", resultado.getNombre());
        assertEquals("muy dulce", resultado.getDescripcion());
        assertEquals(7000, resultado.getPrecio());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(productoExistente);
    }


    ///CARGA DATOS PARA PODER TESTEAR
    public void chargeProducto(){
        Producto prod1 = new Producto(Long.valueOf(1), "Miel Organica","dulce",6000);
        Producto prod2 = new Producto(Long.valueOf(2), "Café Organico","sin cafeina",3000);
        Producto prod3 = new Producto(Long.valueOf(3), "Cepillo de Dientes","de Bambú",4000);
        list.add(prod1);
        list.add(prod2);
        list.add(prod3);
    }

}
