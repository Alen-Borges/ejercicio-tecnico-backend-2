package com.devsu.backend.cliente.model.dto;

import java.io.Serializable;

public record ClienteEvent(
    Long id,
    String nombre,
    String identificacion,
    Boolean estado
) implements Serializable {}
