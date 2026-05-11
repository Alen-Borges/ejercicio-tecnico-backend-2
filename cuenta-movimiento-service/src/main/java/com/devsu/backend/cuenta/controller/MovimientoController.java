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

    @GetMapping
    public ResponseEntity<java.util.List<MovimientoResponse>> getAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponse> update(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
