package com.agendamento.service;

import com.agendamento.dto.ProfissionalRequestDTO;
import com.agendamento.dto.ProfissionalResponseDTO;
import com.agendamento.entity.Profissional;
import com.agendamento.exception.CpfJaCadastradoException;
import com.agendamento.exception.EmailJaCadastradoException;
import com.agendamento.repository.ProfissionalRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;
    private final PasswordEncoder passwordEncoder;
    public ProfissionalService(ProfissionalRepository profissionalRepository, PasswordEncoder passwordEncoder) {
        this.profissionalRepository = profissionalRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public ProfissionalResponseDTO salvar(ProfissionalRequestDTO dados){
        if (profissionalRepository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Já existe um profissional cadastrado com este e-mail.");
        }
        if (profissionalRepository.existsByCpf(dados.getCpf())) {
            throw new CpfJaCadastradoException("Ja existe um profissional cadastrado com este cpf.");
        }
        Profissional profissional = new Profissional();
        profissional.setNome(dados.getNome());
        profissional.setEmail(dados.getEmail());
        profissional.setCpf(dados.getCpf());
        profissional.setSenha(passwordEncoder.encode(dados.getSenha()));
        Profissional salvo = profissionalRepository.save(profissional);
        return new ProfissionalResponseDTO(salvo);
    }
    public Optional<ProfissionalResponseDTO> buscarPorId(Integer id){
         //o .map serve para se tiver um profissional dentro do optional ele converte para response se nao,continua vazio.
        return profissionalRepository.findById(id).map(profissional -> new ProfissionalResponseDTO(profissional));
    }
    public List<ProfissionalResponseDTO> buscarTodos(){
        return profissionalRepository.findAll().stream().map(profissional -> new ProfissionalResponseDTO(profissional))
                .collect(Collectors.toList());
    }
    public Boolean excluir(Integer id){
         boolean profissional = profissionalRepository.existsById(id);
        if(profissional){
            profissionalRepository.deleteById(id);
        }else{
            return profissional;
        }
        return profissional;
    }
    public Optional<ProfissionalResponseDTO> atualizar(Integer id,ProfissionalRequestDTO dados) {
        Optional<Profissional> profissionalOptional = profissionalRepository.findById(id);
        if (profissionalOptional.isEmpty()) {
            return Optional.empty();
        }
//Verificar se o Email e de quem esta atualizando
        Optional<Profissional> profissionalComEmail = profissionalRepository.findByEmail(dados.getEmail());
        if (profissionalComEmail.isPresent() && !profissionalComEmail.get().getId().equals(id)) {
            throw new EmailJaCadastradoException("Já existe um profissional cadastrado com este e-mail");
        }
//Verificar se o CPF e de quem esta atualizando
        Optional<Profissional> profissionalComCpf = profissionalRepository.findByCpf(dados.getCpf());
        if (profissionalComCpf.isPresent() &&  !profissionalComCpf.get().getId().equals(id)) {
            throw new CpfJaCadastradoException("Já existe um profissional cadastrado com este CPF");
        }

        Profissional profissional = profissionalOptional.get();
        profissional.setNome(dados.getNome());
        profissional.setCpf(dados.getCpf());
        profissional.setEmail(dados.getEmail());
        Profissional salvo = profissionalRepository.save(profissional);
        return Optional.of(new ProfissionalResponseDTO(salvo));
    }
    public Optional<ProfissionalResponseDTO> buscarPorEmail(String email){
        return profissionalRepository.findByEmail(email)
                .map(profissional -> new ProfissionalResponseDTO(profissional));
    }


}
