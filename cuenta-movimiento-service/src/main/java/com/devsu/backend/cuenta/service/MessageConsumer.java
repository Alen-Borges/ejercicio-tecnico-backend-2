package com.devsu.backend.cuenta.service;

import com.devsu.backend.cuenta.model.dto.ClienteEvent;
import com.devsu.backend.cuenta.model.entity.Cliente;
import com.devsu.backend.cuenta.repository.ClienteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final ClienteRepository clienteRepository;

    public MessageConsumer(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @RabbitListener(queues = "customer.created.queue")
    public void consume(ClienteEvent event) {
        System.out.println("Consumiendo evento de cliente: " + event.nombre());
        Cliente cliente = Cliente.builder()
                .id(event.id())
                .nombre(event.nombre())
                .build();
        clienteRepository.save(cliente);
    }
}
