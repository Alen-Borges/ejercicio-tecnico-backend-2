package com.devsu.backend.cuenta.repository;

import com.devsu.backend.cuenta.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.clienteId = :clienteId " +
           "AND m.fecha BETWEEN :startDate AND :endDate ORDER BY m.fecha DESC")
    List<Movimiento> findByClienteIdAndFechaBetween(
            @Param("clienteId") Long clienteId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
