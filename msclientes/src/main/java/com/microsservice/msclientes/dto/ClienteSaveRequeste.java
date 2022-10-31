package com.microsservice.msclientes.dto;

import com.microsservice.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequeste {

    private String nome;
    private String cpf;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(nome, cpf, idade);
    }

}
