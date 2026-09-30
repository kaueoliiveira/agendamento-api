package com.agendamento.service;

import com.agendamento.dto.ClienteRequestDTO;
import com.agendamento.dto.ClienteResponseDTO;
import com.agendamento.entity.Cliente;
import com.agendamento.exception.EmailJaCadastradoException;
import com.agendamento.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public ClienteResponseDTO salvar(ClienteRequestDTO dados){
        if(clienteRepository.existsByEmail(dados.getEmail())){
            throw new EmailJaCadastradoException("Já existe uma conta cadastrada com este e-mail.");
        }
        Cliente cliente = new Cliente();
        cliente.setEmail(dados.getEmail());
        cliente.setSenha(passwordEncoder.encode(dados.getSenha()));
        cliente.setNome(dados.getNome());
        cliente.setTelefone(dados.getTelefone());
        Cliente salvo =  clienteRepository.save(cliente);
        return new ClienteResponseDTO(salvo);
    }
    public Optional<ClienteResponseDTO> buscarPorId(Integer id){
        return clienteRepository.findById(id).map(cliente -> new ClienteResponseDTO(cliente));
    }
    public Optional<ClienteResponseDTO> buscarPorEmail(String email){
        return clienteRepository.findByEmail(email).map(cliente -> new ClienteResponseDTO(cliente));
    }
    public boolean excluir(Integer id){
        if(!clienteRepository.existsById(id)){
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }
    public Optional<ClienteResponseDTO> atualizar(ClienteRequestDTO dados,Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            return Optional.empty();
        }
//Verificar se é o email do usuario
        Optional<Cliente> clienteComEmail = clienteRepository.findByEmail(dados.getEmail());
        if(clienteComEmail.isPresent() && !clienteComEmail.get().getId().equals(id)) {
            throw new EmailJaCadastradoException("Já existe um cliente cadastrado com este e-mail");
        }
        Cliente clienteExistente = cliente.get();
        clienteExistente.setTelefone(dados.getTelefone());
        clienteExistente.setNome(dados.getNome());
        clienteExistente.setEmail(dados.getEmail());
        Cliente salvo = clienteRepository.save(clienteExistente);
        return Optional.of(new ClienteResponseDTO(salvo));

    }
}
