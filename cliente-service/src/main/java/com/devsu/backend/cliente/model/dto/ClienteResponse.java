package com.devsu.backend.cliente.model.dto;

public record ClienteResponse(
    Long id,
    String clienteid,
    String nombre,
    String identificacion,
    String direccion,
    String telefono,
    Boolean estado
) {}
