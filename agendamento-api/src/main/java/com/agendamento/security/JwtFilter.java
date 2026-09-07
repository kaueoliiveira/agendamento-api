package com.agendamento.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ProfissionalUserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, ProfissionalUserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
//authHeader.substring(7); tira o "Bearer" deixando so o token
//jwtService.tokenValido(token) → verifica se o token é válido e não expirou
//jwtService.extrairEmail(token) → pega o email de dentro do token
        String token = authHeader.substring(7);
        if(jwtService.tokenValido(token)){
            String email = jwtService.extrairEmail(token);
            //userDetailsService.loadUserByUsername(email)
//Busca o profissional no banco pelo email que veio do token — confirma que ele ainda existe.
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
/*
userDetails - quem é o usuário
null - credenciais (senha) - não precisa pois o token já provou a identidade
userDetails.getAuthorities() - permissões do usuário*/
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

//Adiciona detalhes da requisição (como ip)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//Autentica o usuario para o Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

}
