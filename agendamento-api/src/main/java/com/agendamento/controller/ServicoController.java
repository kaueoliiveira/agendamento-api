package com.agendamento.controller;

import com.agendamento.dto.ServicoRequestDTO;
import com.agendamento.dto.ServicoResponseDTO;
import com.agendamento.exception.ProfissionalNaoEncontradoException;
import com.agendamento.service.ProfissionalService;
import com.agendamento.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        Integer profissionalId = getProfissionalAutenticado();
        ServicoResponseDTO response = servicoService.salvar(dados, profissionalId);
        return ResponseEntity.status(201).body(response);

    }
    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> buscarServicos(){
        Integer profissionalId = getProfissionalAutenticado();
        return ResponseEntity.ok(servicoService.buscarPorProfissional(profissionalId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> buscarServicoPorId(@PathVariable Integer id){
        return servicoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoRequestDTO dados){
        Integer profissionalId = getProfissionalAutenticado();
        return servicoService.atualizar(id, dados, profissionalId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServicoPorId(@PathVariable Integer id){
        Integer profissionalId = getProfissionalAutenticado();
        boolean excluido = servicoService.excluir(id, profissionalId);
        if(excluido){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    private Integer getProfissionalAutenticado(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return profissionalService.buscarPorEmail(email)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado")).getId();
    }



}
