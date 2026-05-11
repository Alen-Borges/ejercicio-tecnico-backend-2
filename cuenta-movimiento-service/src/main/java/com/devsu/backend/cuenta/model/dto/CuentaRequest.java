package com.devsu.backend.cuenta.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CuentaRequest(
    @NotBlank(message = "El número de cuenta es obligatorio")
    String numeroCuenta,

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    String tipoCuenta,

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial debe ser mayor o igual a cero")
    BigDecimal saldoInicial,

    @NotNull(message = "El estado es obligatorio")
    Boolean estado,

    @NotNull(message = "El ID de cliente es obligatorio")
    Long clienteId
) {}
