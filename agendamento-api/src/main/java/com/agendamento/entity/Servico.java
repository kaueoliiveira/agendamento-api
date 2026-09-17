package com.agendamento.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
public class Servico {

//Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "preco")
    private BigDecimal preco;
    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

//Getters e Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//Contrutor


    public Servico(Integer id, String nome, BigDecimal preco, Integer duracaoMinutos, Profissional profissional) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
        this.profissional = profissional;
    }

//Construtor vazio


    public Servico() {
    }
}
