package com.agendamento.controller;

import com.agendamento.dto.LoginRequestDTO;
import com.agendamento.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequestDTO dados){
        //cria um objeto com os dados que o usuario mandou
        var autenticacao = new UsernamePasswordAuthenticationToken(dados.getEmail(),dados.getSenha());
        //verifica se o email e senha esta certo. (se errado lança um exception e se certo continua)
        authenticationManager.authenticate(autenticacao);
        //gera o token se estiver certo.
        String token = jwtService.gerarToken(dados.getEmail());
        //retorna o token para quem fez o login
        return ResponseEntity.ok(token);
    }
}

