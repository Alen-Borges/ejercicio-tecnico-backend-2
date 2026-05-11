package com.devsu.backend.cuenta.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MovimientoRequest(
    @NotBlank(message = "El número de cuenta es obligatorio")
    String numeroCuenta,

    @NotNull(message = "El valor del movimiento es obligatorio")
    BigDecimal valor
) {}
