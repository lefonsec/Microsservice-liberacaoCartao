package com.microsservice.msavaliadorcredito.controller.ex;

public class ErroSolicitacao extends  RuntimeException{
    public ErroSolicitacao(String message) {
        super(message);
    }
}
