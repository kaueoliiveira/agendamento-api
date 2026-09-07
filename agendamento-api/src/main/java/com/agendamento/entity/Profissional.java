package com.agendamento.entity;

import com.agendamento.validation.ValidCpf;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "profissionais")

public class Profissional {

//Atributos

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um endereço de e-mail válido")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "A senha é obrigatória!")
    @Size(min = 6,message = "A senha deve ter no mínimo 6 caracteres")
    @Column(name = "senha")
    private String senha;
    @ValidCpf
    @Column(name = "cpf")
    private String cpf;

//Getters e Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

//Construtor


    public Profissional(Integer id, String nome, String email, String senha, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

//Construtor vazio

    public Profissional() {}
}
