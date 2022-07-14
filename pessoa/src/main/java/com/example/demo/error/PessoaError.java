package com.example.demo.error;

public class PessoaError extends RuntimeException {
    public PessoaError(String mensagemError){
        super(mensagemError);
    }
}
