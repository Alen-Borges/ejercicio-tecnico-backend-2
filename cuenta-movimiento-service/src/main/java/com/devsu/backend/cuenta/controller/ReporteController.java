package com.devsu.backend.cuenta.controller;

import com.devsu.backend.cuenta.model.dto.ReporteResponse;
import com.devsu.backend.cuenta.service.MovimientoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final MovimientoService movimientoService;

    public ReporteController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponse>> getReporte(
            @RequestParam("fecha") String rangoFechas,
            @RequestParam("cliente") Long clienteId) {
        
        // Split rango "YYYY-MM-DD,YYYY-MM-DD"
        String[] dates = rangoFechas.split(",");
        LocalDateTime start = LocalDate.parse(dates[0]).atStartOfDay();
        LocalDateTime end = LocalDate.parse(dates[1]).atTime(LocalTime.MAX);

        return ResponseEntity.ok(movimientoService.getReporte(clienteId, start, end));
    }
}
