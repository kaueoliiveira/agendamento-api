package com.agendamento.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

//pega o valor do token do properties

    @Value("${jwt.secret}")
    private String secret;

//pega o tempo para expirar do properties

    @Value("${jwt.expiration}")
    private long expiration;

//converte a chave para que a biblioteca consiga ler
    private SecretKey getChave(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
//           --Metodo gerarToken--
//.subject(email) - guarda o email dentro do token
//.issuedAt(new Date()) - registra quando o token foi criado
//.expiration(...) - define quando expira (agora + 1 hora)
//.signWith(getChave()) - assina com a chave secreta
//.compact() - gera a String final do token

    public String gerarToken(String email){
        return Jwts.builder().subject(email).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getChave()).compact();
    }

//          ---Metodo extrairEmail----
//.verifyWith(getChave()) → usa a chave para verificar a assinatura
//.build() → cria o parser
//.parseSignedClaims(token) → lê e valida o token
//.getPayload() → pega o conteúdo interno
//.getSubject() → retorna o email (que você guardou com .subject())

    public String extrairEmail(String token){
        return Jwts.parser().verifyWith(getChave())
                .build().parseSignedClaims(token).getPayload().getSubject();
    }

//         ---tokenValido---
//Jwts.parser() - cria o leitor do token
//.verifyWith(getChave()) - configura a chave para verificar a assinatura
//.build() - finaliza a configuração do leitor
//.parseSignedClaims(token) - lê e valida o token

    public boolean tokenValido(String token){
        try{
            Jwts.parser().verifyWith(getChave()).build().parseSignedClaims(token);
            return true;
        }catch(Exception e){return false;}
    }

}
