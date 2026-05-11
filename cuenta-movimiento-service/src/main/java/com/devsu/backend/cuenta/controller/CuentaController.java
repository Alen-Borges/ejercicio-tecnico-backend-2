package com.devsu.backend.cuenta.controller;

import com.devsu.backend.cuenta.model.dto.CuentaRequest;
import com.devsu.backend.cuenta.model.dto.CuentaResponse;
import com.devsu.backend.cuenta.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> create(@Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> getAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> update(@PathVariable Long id, @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(cuentaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
