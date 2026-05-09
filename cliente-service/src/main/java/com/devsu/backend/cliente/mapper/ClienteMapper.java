package com.devsu.backend.cliente.mapper;

import com.devsu.backend.cliente.model.dto.ClienteRequest;
import com.devsu.backend.cliente.model.dto.ClienteResponse;
import com.devsu.backend.cliente.model.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .nombre(request.nombre())
                .genero(request.genero())
                .edad(request.edad())
                .identificacion(request.identificacion())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .contrasena(request.contrasena())
                .estado(request.estado())
                .clienteid(UUID.randomUUID().toString()) // Generamos el clienteid único
                .build();
    }

    public ClienteResponse toResponse(Cliente entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getClienteid(),
                entity.getNombre(),
                entity.getIdentificacion(),
                entity.getDireccion(),
                entity.getTelefono(),
                entity.getEstado()
        );
    }
}
