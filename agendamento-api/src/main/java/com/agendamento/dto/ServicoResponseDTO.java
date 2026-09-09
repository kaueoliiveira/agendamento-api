package com.agendamento.dto;

import com.agendamento.entity.Servico;

import java.math.BigDecimal;

public class ServicoResponseDTO {

//Atributos

    private Integer id;
    private String nome;
    private BigDecimal preco;
    private Integer duracaoMinutos;

//Getters e Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer idServico) {
        this.id = idServico;
    }

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

    public ServicoResponseDTO(Servico servico) {
        this.id = servico.getId();
        this.duracaoMinutos = servico.getDuracaoMinutos();
        this.preco = servico.getPreco();
        this.nome = servico.getNome();
    }
}
