package com.agendamento.repository;

import com.agendamento.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Profissional> findByCpf(String cpf);
    Optional<Profissional> findByEmail(String email);

}
