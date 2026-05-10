package com.devsu.backend.cuenta.glue;

import com.devsu.backend.cuenta.model.dto.MovimientoRequest;
import com.devsu.backend.cuenta.model.entity.Cuenta;
import com.devsu.backend.cuenta.repository.CuentaRepository;
import com.devsu.backend.cuenta.repository.MovimientoRepository;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Cuando;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovimientosGlue {

    @LocalServerPort
    private int port;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    private Response response;

    @Dado("que existe una cuenta con número {string} y saldo inicial de {int}")
    public void queExisteUnaCuentaConNúmeroYSaldoInicialDe(String numero, int saldo) {
        cuentaRepository.findByNumeroCuenta(numero).ifPresent(cuenta -> {
            movimientoRepository.deleteAll(movimientoRepository.findByCuenta(cuenta));
            cuentaRepository.delete(cuenta);
        });
        
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(numero)
                .tipoCuenta("Ahorros")
                .saldoInicial(new BigDecimal(saldo))
                .estado(true)
                .clienteId(1L)
                .build();
        cuentaRepository.save(cuenta);
    }

    @Cuando("realizo un depósito de {int} a la cuenta {string}")
    public void realizoUnDepósitoDeALaCuenta(int valor, String numero) {
        MovimientoRequest request = new MovimientoRequest(numero, new BigDecimal(valor));
        
        response = SerenityRest.given()
                .baseUri("http://localhost:" + port)
                .contentType("application/json")
                .body(request)
                .post("/movimientos");
    }

    @Cuando("realizo un retiro de {int} de la cuenta {string}")
    public void realizoUnRetiroDeDeLaCuenta(int valor, String numero) {
        // Enviar valor negativo para retiro según lógica de negocio
        MovimientoRequest request = new MovimientoRequest(numero, new BigDecimal(valor).negate());
        
        response = SerenityRest.given()
                .baseUri("http://localhost:" + port)
                .contentType("application/json")
                .body(request)
                .post("/movimientos");
    }

    @Entonces("el saldo disponible debe ser {int}")
    public void elSaldoDisponibleDebeSer(int saldoEsperado) {
        response.then()
                .statusCode(201)
                .body("saldo", equalTo((float) saldoEsperado));
    }

    @Entonces("recibo un error con el mensaje {string}")
    public void reciboUnErrorConElMensaje(String mensajeEsperado) {
        response.then()
                .statusCode(400)
                .body("message", equalTo(mensajeEsperado));
    }
}
