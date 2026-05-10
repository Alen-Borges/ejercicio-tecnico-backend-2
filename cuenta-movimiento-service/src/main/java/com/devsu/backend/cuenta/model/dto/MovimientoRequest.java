package com.devsu.backend.cuenta.model.dto;

import java.math.BigDecimal;

public record MovimientoRequest(
    String numeroCuenta,
    BigDecimal valor
) {}
