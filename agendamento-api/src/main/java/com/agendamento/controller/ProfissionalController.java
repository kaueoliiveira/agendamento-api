package com.agendamento.controller;

import com.agendamento.dto.ProfissionalResponseDTO;
import com.agendamento.service.ProfissionalService;
import jakarta.validation.Valid;
import com.agendamento.dto.ProfissionalRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private final ProfissionalService profissionalService;
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping
    public ProfissionalResponseDTO save(@Valid @RequestBody ProfissionalRequestDTO dados){
        return profissionalService.salvar(dados);
    }
    @GetMapping("/me")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorEmail(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ProfissionalResponseDTO> profissional = profissionalService.buscarPorEmail(email);
        if(profissional.isPresent()){
            return ResponseEntity.ok(profissional.get());}
        else{
            return ResponseEntity.notFound().build();}
    }
    @DeleteMapping("/me")
    public ResponseEntity<Void> excluir(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ProfissionalResponseDTO> profissional = profissionalService.buscarPorEmail(email);
        if (profissional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        int id = profissional.get().getId();
        boolean existe = profissionalService.excluir(id);
        if(existe){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();}
    }
    @PutMapping("/me")
    public ResponseEntity<ProfissionalResponseDTO> atualizar(@Valid @RequestBody ProfissionalRequestDTO dados) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ProfissionalResponseDTO> profissional = profissionalService.buscarPorEmail(email);
        if (profissional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return profissionalService.atualizar(profissional.get().getId(), dados)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }



}
