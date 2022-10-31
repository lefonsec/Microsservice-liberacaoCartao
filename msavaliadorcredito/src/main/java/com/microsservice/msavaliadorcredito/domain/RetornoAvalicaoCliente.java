package com.microsservice.msavaliadorcredito.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetornoAvalicaoCliente {
    private List<CartaoAprovado> cartoes;
}
