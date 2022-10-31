package com.microsservice.mscartoes.repositories;

import com.microsservice.mscartoes.model.Cartao;
import com.microsservice.mscartoes.model.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
