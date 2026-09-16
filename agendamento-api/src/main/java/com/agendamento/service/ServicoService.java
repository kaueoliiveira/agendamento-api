package com.agendamento.service;

import com.agendamento.dto.ServicoRequestDTO;
import com.agendamento.dto.ServicoResponseDTO;
import com.agendamento.entity.Profissional;
import com.agendamento.entity.Servico;
import com.agendamento.exception.ProfissionalNaoEncontradoException;
import com.agendamento.repository.ProfissionalRepository;
import com.agendamento.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoService {
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;
    public ServicoService(ServicoRepository servicoRepository, ProfissionalRepository profissionalRepository) {
        this.servicoRepository = servicoRepository;
        this.profissionalRepository = profissionalRepository;
    }
    public ServicoResponseDTO salvar(ServicoRequestDTO dados,Integer profissionalId) {
        Optional<Profissional> profissional = profissionalRepository.findById(profissionalId);
        if  (profissional.isEmpty()) {
            throw new ProfissionalNaoEncontradoException("Profissional não encontrado!");

        }
        Servico servico = new Servico(null,
                dados.getNome(), dados.getPreco(), dados.getDuracaoMinutos(), profissional.get());
        return new ServicoResponseDTO(servicoRepository.save(servico));
    }
    public List<ServicoResponseDTO> buscarPorProfissional(Integer id) {
       List<Servico> servicos = servicoRepository.findByProfissionalId(id);
    /* .Stream pega cada elemento para modificar
    .map pega cada elemento e transforma de servico -> (para) servicoResponseDTO
    .collect coleta tudo e junta em uma lista novamente. */
       return servicos.stream().map(servico -> new ServicoResponseDTO(servico)).collect(Collectors.toList());

    }
    public Optional<ServicoResponseDTO> buscarPorId(Integer id) {
        return servicoRepository.findById(id).map(servico -> new ServicoResponseDTO(servico));
    }
    public boolean excluir(Integer id,Integer profissionalId) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if (servico.isEmpty()) {
            return false;
        }
        if(!servico.get().getProfissional().getId().equals(profissionalId)) {
            return false;
        }
        servicoRepository.deleteById(id);
        return true;
    }
    public Optional<ServicoResponseDTO> atualizar(Integer id, ServicoRequestDTO dados,Integer profissionalId) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if (!servico.isPresent()) {
            return Optional.empty();
        }
        Servico servicoAtualizado = servico.get();
        if(!servicoAtualizado.getProfissional().getId().equals(profissionalId)) {
            return Optional.empty();
        }
        servicoAtualizado.setNome(dados.getNome());
        servicoAtualizado.setPreco(dados.getPreco());
        servicoAtualizado.setDuracaoMinutos(dados.getDuracaoMinutos());
        Servico salvo = servicoRepository.save(servicoAtualizado);
        return Optional.of(new ServicoResponseDTO(salvo));
    }
}
