package com.devsu.backend.cuenta.mapper;

import com.devsu.backend.cuenta.model.dto.MovimientoResponse;
import com.devsu.backend.cuenta.model.entity.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public MovimientoResponse toResponse(Movimiento entity) {
        return new MovimientoResponse(
                entity.getId(),
                entity.getFecha(),
                entity.getTipoMovimiento(),
                entity.getValor(),
                entity.getSaldo(),
                entity.getCuenta().getNumeroCuenta()
        );
    }
}
