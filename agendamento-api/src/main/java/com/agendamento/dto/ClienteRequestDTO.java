package com.agendamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {
    @NotBlank(message = "O nome é obrigatório!")
    private String nome;
    @Email(message = "O e-mail é inválido!")
    @NotBlank(message = "O e-mail é obrigatório!")
    private String email;
    @NotBlank(message = "A senha é obrigatória!")
    @Size(min = 6,message = "A senha deve ter no minimo 6 caracteres")
    private String senha;
    @NotBlank(message = "O telefone é obrigatório!")
    @Size(min = 10,max = 15,message = "telefone inválido!")
    private String telefone;


//Getters e Setters


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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
