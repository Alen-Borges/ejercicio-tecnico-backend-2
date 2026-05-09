package com.devsu.backend.cuenta.controller;

import com.devsu.backend.cuenta.model.dto.MovimientoRequest;
import com.devsu.backend.cuenta.model.dto.MovimientoResponse;
import com.devsu.backend.cuenta.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> create(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.create(request));
    }
}
