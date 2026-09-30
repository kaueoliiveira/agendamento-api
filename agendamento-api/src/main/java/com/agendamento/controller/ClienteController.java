package com.agendamento.controller;

import com.agendamento.dto.ClienteRequestDTO;
import com.agendamento.dto.ClienteResponseDTO;
import com.agendamento.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ClienteResponseDTO salvar(@Valid @RequestBody ClienteRequestDTO dados){
        return clienteService.salvar(dados);
    }

}
