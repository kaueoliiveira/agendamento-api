package com.agendamento.controller;

import com.agendamento.dto.ServicoRequestDTO;
import com.agendamento.dto.ServicoResponseDTO;
import com.agendamento.service.ProfissionalService;
import com.agendamento.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;

    public ServicoController(ServicoService servicoService, ProfissionalService profissionalService) {
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
    }
    @PostMapping
    public ResponseEntity<ServicoResponseDTO> salvar(@RequestBody @Valid ServicoRequestDTO dados) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer profissionalId = profissionalService.buscarPorEmail(email).get().getId();
        ServicoResponseDTO response = servicoService.salvar(dados, profissionalId);
        return ResponseEntity.status(201).body(response);

    }


}
