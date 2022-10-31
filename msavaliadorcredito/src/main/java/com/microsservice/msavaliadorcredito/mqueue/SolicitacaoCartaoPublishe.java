package com.microsservice.msavaliadorcredito.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsservice.msavaliadorcredito.domain.DadosEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoCartaoPublishe {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartao;


    public void solicitarCarto(DadosEmissaoCartao dados) throws JsonProcessingException {
        String json = this.convertIntoJson(dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartao.getName(),json);

    }

    private String convertIntoJson(DadosEmissaoCartao dadosEmissaoCartao) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dadosEmissaoCartao);
        return json;
    }
}
