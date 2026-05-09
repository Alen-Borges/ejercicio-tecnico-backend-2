package com.devsu.backend.cliente.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRequest(
    @NotBlank String nombre,
    String genero,
    Integer edad,
    @NotBlank String identificacion,
    String direccion,
    String telefono,
    @NotBlank String contrasena,
    @NotNull Boolean estado
) {}
