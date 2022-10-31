package com.microsservice.mscartoes.servicies;

import com.microsservice.mscartoes.model.ClienteCartao;
import com.microsservice.mscartoes.repositories.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Digits;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;


    public List<ClienteCartao> listCartaoByCpf(String cpf){
        return  repository.findByCpf(cpf);
    }
}
