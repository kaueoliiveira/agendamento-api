package com.agendamento.security;

import com.agendamento.repository.ProfissionalRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalUserDetailsService implements UserDetailsService {
    private final ProfissionalRepository profissionalRepository;

    public ProfissionalUserDetailsService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        return profissionalRepository.findByEmail(email).map(profissional -> User.builder()
                .username(profissional.getEmail()).password(profissional.getSenha()).build())
                .orElseThrow(() -> new UsernameNotFoundException("Profissional não Encontrado"));
    }
}
