package com.devsu.backend.cliente.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testClienteCreation() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose Lema");
        cliente.setContrasena("1234");
        cliente.setEstado(true);
        cliente.setIdentificacion("1234567890");

        assertEquals("Jose Lema", cliente.getNombre());
        assertEquals("1234", cliente.getContrasena());
        assertTrue(cliente.getEstado());
        assertEquals("1234567890", cliente.getIdentificacion());
    }

    @Test
    void testClienteInheritance() {
        Cliente cliente = Cliente.builder()
                .nombre("Marianela Montalvo")
                .genero("Femenino")
                .edad(28)
                .identificacion("0987654321")
                .direccion("Amazonas y NNUU")
                .telefono("097548965")
                .clienteid("C002")
                .contrasena("5678")
                .estado(true)
                .build();

        assertNotNull(cliente);
        assertEquals("Marianela Montalvo", cliente.getNombre());
        assertEquals("Femenino", cliente.getGenero());
        assertEquals(28, cliente.getEdad());
        assertEquals("C002", cliente.getClienteid());
    }
}
