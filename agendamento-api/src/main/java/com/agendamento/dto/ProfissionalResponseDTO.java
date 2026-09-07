package com.agendamento.dto;

import com.agendamento.entity.Profissional;

public class ProfissionalResponseDTO {

//Atributos
    private Integer id;
    private String nome;
    private String email;
    private String cpf;

//Getters e Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//Construtor do Response recebendo um Profissional

    public ProfissionalResponseDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.nome = profissional.getNome();
        this.email = profissional.getEmail();
        this.cpf = profissional.getCpf();
    }


}
