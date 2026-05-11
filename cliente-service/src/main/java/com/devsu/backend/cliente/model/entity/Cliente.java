package com.devsu.backend.cliente.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

    @Column(unique = true, nullable = false)
    private String clienteid;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;
}
