package com.devsu.backend.cuenta.mapper;

import com.devsu.backend.cuenta.model.dto.CuentaRequest;
import com.devsu.backend.cuenta.model.dto.CuentaResponse;
import com.devsu.backend.cuenta.model.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toEntity(CuentaRequest request) {
        return Cuenta.builder()
                .numeroCuenta(request.numeroCuenta())
                .tipoCuenta(request.tipoCuenta())
                .saldoInicial(request.saldoInicial())
                .estado(request.estado())
                .clienteId(request.clienteId())
                .build();
    }

    public CuentaResponse toResponse(Cuenta entity) {
        return new CuentaResponse(
                entity.getId(),
                entity.getNumeroCuenta(),
                entity.getTipoCuenta(),
                entity.getSaldoInicial(),
                entity.getEstado(),
                entity.getClienteId()
        );
    }
}
