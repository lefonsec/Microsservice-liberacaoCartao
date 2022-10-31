package com.microsservice.mscartoes.servicies;

import com.microsservice.mscartoes.model.Cartao;
import com.microsservice.mscartoes.repositories.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao) {
      return repository.save(cartao);
    }

    public List<Cartao> getcartoesRendaMenorIgual(Long renda){
        BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
