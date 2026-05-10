package com.devsu.backend.cuenta.model.dto;

import java.math.BigDecimal;

public record CuentaResponse(
    Long id,
    String numeroCuenta,
    String tipoCuenta,
    BigDecimal saldoInicial,
    Boolean estado,
    Long clienteId
) {}
