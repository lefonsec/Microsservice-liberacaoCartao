package com.microsservice.mscartoes.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsservice.mscartoes.model.Cartao;
import com.microsservice.mscartoes.model.ClienteCartao;
import com.microsservice.mscartoes.model.DadosEmissaoCartao;
import com.microsservice.mscartoes.repositories.CartaoRepository;
import com.microsservice.mscartoes.repositories.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscribe {

    private final CartaoRepository repository;
    private final ClienteCartaoRepository clienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DadosEmissaoCartao dados = mapper.readValue(payload, DadosEmissaoCartao.class);
            Cartao cartao = repository.findById(dados.getIdCartao()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();

            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());
            clienteRepository.save(clienteCartao);

        }catch (JsonProcessingException e ){
            log.error("error ao receber solicitação se emisao de cartao:" + e.getMessage());
        }

    }
}
