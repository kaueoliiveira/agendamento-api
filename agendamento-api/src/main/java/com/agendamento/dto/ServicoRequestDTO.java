package com.agendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ServicoRequestDTO {
    @NotBlank
    @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres!")
    private String nome;
    @NotNull
    @Positive
    private BigDecimal preco;
    @NotNull
    @Positive
    private Integer duracaoMinutos;
    @NotNull
    private Integer profissionalId;

//Getters e Setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Integer getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Integer profissionalId) {
        this.profissionalId = profissionalId;
    }
}
