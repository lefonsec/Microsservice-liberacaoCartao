package com.microsservice.msavaliadorcredito.controller.ex;

import lombok.Getter;

public class ErroComunicacaoException extends Exception{
    @Getter
    private Integer status;

    public ErroComunicacaoException(String message,Integer status) {
        super(message);
        this.status = status;
    }
}
