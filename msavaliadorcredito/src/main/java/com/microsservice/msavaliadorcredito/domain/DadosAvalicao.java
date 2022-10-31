package com.microsservice.msavaliadorcredito.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DadosAvalicao {

    private String cpf;
    private Long renda;
}
