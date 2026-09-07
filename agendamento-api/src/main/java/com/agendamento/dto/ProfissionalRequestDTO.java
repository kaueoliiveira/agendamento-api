package com.agendamento.dto;

import com.agendamento.validation.ValidCpf;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfissionalRequestDTO {

//Atributos

    @NotBlank(message = "O nome é obrigatório!")
    private String nome;
    @Email(message = "O e-mail é inválido! ")
    @NotBlank(message = "O e-mail é obrigatório!")
    private String email;
    @Size(min = 6,message = "A senha deve ter no mínimo 6 caracteres!")
    @NotBlank(message = "A senha é obrigatória!")
    private String senha;
    @ValidCpf
    @NotBlank(message = "O CPF é obrigatório!")
    private String cpf;

//Getters e Setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
