package com.agendamento.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final ProfissionalUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, ProfissionalUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    //Esse metodo representa toda a segurança:rotas,filtros,sessão,CSRF.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        //.csrf(csrf -> csrf.disable()) : Desabilita o CSRF (nao precisa pois o token nao fica nos cookies na api rest)
                .csrf(csrf -> csrf.disable())
        //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Diz ao Spring Security para não criar sessão — cada requisição é independente e usa o token
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.authorizeHttpRequests(auth -> auth...) : Define as regras de acesso
                .authorizeHttpRequests(auth -> auth.
                //.requestMatchers("/auth/login", "/profissionais").permitAll() :  rotas  públicas
                //.anyRequest().authenticated() : as outras precisam de token
                        requestMatchers(HttpMethod.POST,"/auth/login","/profissionais").permitAll().anyRequest().authenticated())
                /*.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class): Adiciona o JwtFilter antes do
                filtro padrão do Spring e garante que o token seja validado antes de qualquer coisa. */
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
    @Bean
/*PasswordEncoder — interface do Spring Security para encodar senhas.
O BCryptPasswordEncoder é a implementação que usa o algoritmo BCrypt.*/
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
/*AuthenticationManager é o que o AuthController chama para verificar se o email e a senha esta corretos
usa o ProfissionalUserDetailsService para buscar no bano e o PasswordEncoder para comparar a senha hashada*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

}
