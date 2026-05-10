package com.devsu.backend.cuenta.integration;

import com.devsu.backend.cuenta.model.dto.MovimientoRequest;
import com.devsu.backend.cuenta.model.dto.MovimientoResponse;
import com.devsu.backend.cuenta.model.entity.Cuenta;
import com.devsu.backend.cuenta.repository.CuentaRepository;
import com.devsu.backend.cuenta.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MovimientoIntegrationTest {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testRegisterMovementUpdateBalance() {
        // Given
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta("478758")
                .tipoCuenta("Ahorros")
                .saldoInicial(new BigDecimal("2000.00"))
                .estado(true)
                .clienteId(1L)
                .build();
        cuentaRepository.save(cuenta);

        MovimientoRequest request = new MovimientoRequest("478758", new BigDecimal("-575.00"));

        // When
        MovimientoResponse response = movimientoService.create(request);

        // Then
        assertEquals(new BigDecimal("1425.00"), response.saldo());
        
        Cuenta updatedCuenta = cuentaRepository.findByNumeroCuenta("478758").orElseThrow();
        assertEquals(new BigDecimal("1425.00"), updatedCuenta.getSaldoInicial());
    }
}
