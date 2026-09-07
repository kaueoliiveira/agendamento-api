package com.agendamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErroValidacao(MethodArgumentNotValidException exception){
        var erros = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(erro -> erro.getField(),erro -> erro.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);

    }
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> tratarErroEmailJaCadastrado(EmailJaCadastradoException exception){
        var erros = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<String> tratarErroCpfJaCadastrado(CpfJaCadastradoException exception){
        var erros = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}

