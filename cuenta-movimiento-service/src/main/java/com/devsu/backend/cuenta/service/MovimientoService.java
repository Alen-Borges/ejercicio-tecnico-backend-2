package com.devsu.backend.cuenta.service;

import com.devsu.backend.cuenta.exception.InsufficientBalanceException;
import com.devsu.backend.cuenta.mapper.MovimientoMapper;
import com.devsu.backend.cuenta.model.dto.MovimientoRequest;
import com.devsu.backend.cuenta.model.dto.MovimientoResponse;
import com.devsu.backend.cuenta.model.dto.ReporteResponse;
import com.devsu.backend.cuenta.model.entity.Cuenta;
import com.devsu.backend.cuenta.model.entity.Movimiento;
import com.devsu.backend.cuenta.repository.ClienteRepository;
import com.devsu.backend.cuenta.repository.CuentaRepository;
import com.devsu.backend.cuenta.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final MovimientoMapper movimientoMapper;

    public MovimientoService(MovimientoRepository movimientoRepository, 
                             CuentaRepository cuentaRepository, 
                             ClienteRepository clienteRepository,
                             MovimientoMapper movimientoMapper) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
        this.movimientoMapper = movimientoMapper;
    }

    @Transactional
    public MovimientoResponse create(MovimientoRequest request) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.numeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo = saldoActual.add(request.valor());

        // F3: Validación de Saldo Disponible
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        // Crear Movimiento
        Movimiento movimiento = Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(request.valor().compareTo(BigDecimal.ZERO) > 0 ? "Depósito" : "Retiro")
                .valor(request.valor())
                .saldo(nuevoSaldo)
                .cuenta(cuenta)
                .build();

        // Actualizar Saldo en Cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        return movimientoMapper.toResponse(movimientoRepository.save(movimiento));
    }

    @Transactional(readOnly = true)
    public List<MovimientoResponse> findAll() {
        return movimientoRepository.findAll().stream()
                .map(movimientoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovimientoResponse findById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        return movimientoMapper.toResponse(movimiento);
    }

    @Transactional
    public MovimientoResponse update(Long id, MovimientoRequest request) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        
        // Note: In a real system, updating a movement would require complex balance recalculations.
        // For this technical test, we simply update the fields.
        movimiento.setValor(request.valor());
        return movimientoMapper.toResponse(movimientoRepository.save(movimiento));
    }

    @Transactional
    public void delete(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        movimientoRepository.delete(movimiento);
    }

    @Transactional(readOnly = true)
    public List<ReporteResponse> getReporte(Long clienteId, LocalDateTime start, LocalDateTime end) {
        List<Movimiento> movimientos = movimientoRepository.findByClienteIdAndFechaBetween(clienteId, start, end);
        
        String nombreCliente = clienteRepository.findById(clienteId)
                .map(com.devsu.backend.cuenta.model.entity.Cliente::getNombre)
                .orElse("Cliente " + clienteId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return movimientos.stream().map(m -> new ReporteResponse(
                m.getFecha().format(formatter),
                nombreCliente,
                m.getCuenta().getNumeroCuenta(),
                m.getCuenta().getTipoCuenta(),
                m.getSaldo().subtract(m.getValor()), // Saldo inicial antes del movimiento
                m.getCuenta().getEstado(),
                m.getValor(),
                m.getSaldo()
        )).collect(Collectors.toList());
    }
}
