package com.microsservice.mscartoes.dto;

import com.microsservice.mscartoes.model.Cartao;
import com.microsservice.mscartoes.model.enuns.BandeiraCartao;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel() {
        return new Cartao(nome, bandeira,renda, limiteBasico);
    }
}
