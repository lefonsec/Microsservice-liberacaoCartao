package com.microsservice.msavaliadorcredito.controller.ex;

public class DadosClienteException extends Exception {

    public DadosClienteException() {
        super("dados do Cliente não encontrados para o cpf informado.");
    }
}
