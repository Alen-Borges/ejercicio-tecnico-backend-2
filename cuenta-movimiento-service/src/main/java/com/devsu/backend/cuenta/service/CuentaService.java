package com.devsu.backend.cuenta.service;

import com.devsu.backend.cuenta.mapper.CuentaMapper;
import com.devsu.backend.cuenta.model.dto.CuentaRequest;
import com.devsu.backend.cuenta.model.dto.CuentaResponse;
import com.devsu.backend.cuenta.model.entity.Cuenta;
import com.devsu.backend.cuenta.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    public CuentaService(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Transactional
    public CuentaResponse create(CuentaRequest request) {
        cuentaRepository.findByNumeroCuenta(request.numeroCuenta())
                .ifPresent(c -> { throw new RuntimeException("El número de cuenta ya existe"); });

        Cuenta cuenta = cuentaMapper.toEntity(request);
        return cuentaMapper.toResponse(cuentaRepository.save(cuenta));
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> findAll() {
        return cuentaRepository.findAll().stream()
                .filter(Cuenta::getEstado) // Solo cuentas activas
                .map(cuentaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaResponse findByNumero(String numero) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numero)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return cuentaMapper.toResponse(cuenta);
    }

    @Transactional(readOnly = true)
    public CuentaResponse findById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        return cuentaMapper.toResponse(cuenta);
    }

    @Transactional
    public CuentaResponse update(Long id, CuentaRequest request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        
        cuenta.setNumeroCuenta(request.numeroCuenta());
        cuenta.setTipoCuenta(request.tipoCuenta());
        cuenta.setSaldoInicial(request.saldoInicial());
        cuenta.setEstado(request.estado());
        cuenta.setClienteId(request.clienteId());

        return cuentaMapper.toResponse(cuentaRepository.save(cuenta));
    }

    @Transactional
    public void delete(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        cuenta.setEstado(false); // Baja lógica
        cuentaRepository.save(cuenta);
    }
}
