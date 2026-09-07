package com.agendamento.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
//Verifica se é nullo
        if (cpf == null){
            return false;
        }
//Deixa o cpf sem . ou -
        String cpfLimpo = cpf.replaceAll("\\D","");
//Verifica se contem 11 caracteres
        if (cpfLimpo.length() != 11) {
            return false;
        }
//Verifica se os numeros sao todos iguais
        boolean todosIguais = true;
        char primeiro = cpfLimpo.charAt(0);
        for (int i = 1;i < cpfLimpo.length();i++) {
            if (cpfLimpo.charAt(i) != primeiro) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            return false;
        }

//Calcula o digito 1

        int multiplicador1 = 10;
        int digito1 = 0;
        for (int i = 0 ;i < 9;i++) {
           digito1 += Character.getNumericValue(cpfLimpo.charAt(i)) * multiplicador1 ;
           multiplicador1--;
        }
        digito1 %= 11;
        if (digito1 < 2){digito1 = 0;}
        else {digito1 = 11 - digito1;}
//Verifica se o digito 1 esta correto
        int primeiroDigitoCpf = Character.getNumericValue(cpfLimpo.charAt(9));
        if (digito1 != primeiroDigitoCpf) {
            return false;
        }
//Calcula o digito2
        multiplicador1 = 11;
        int[] numeros = new int[cpfLimpo.length()];
        for (int i = 0 ;i < cpfLimpo.length();i++) {
            numeros[i] = Character.getNumericValue(cpfLimpo.charAt(i));
        }
        int digito2 = 0;
        for (int i = 0 ;i < 10;i++) {
            digito2 += numeros[i] * multiplicador1;
            multiplicador1--;

        }
        digito2 %= 11;
        if (digito2 < 2){digito2 = 0;}
        else{digito2 = 11 - digito2;}
//Verifica se o digito 2 esta correto
        int ultimoDigito = Character.getNumericValue(cpfLimpo.charAt(10));
        if (digito2 != ultimoDigito){
            return false;
        }
        return true;

    }




}
