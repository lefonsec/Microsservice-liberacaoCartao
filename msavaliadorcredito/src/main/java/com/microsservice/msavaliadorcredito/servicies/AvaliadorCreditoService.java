package com.microsservice.msavaliadorcredito.servicies;

import com.microsservice.msavaliadorcredito.controller.ex.DadosClienteException;
import com.microsservice.msavaliadorcredito.controller.ex.ErroComunicacaoException;
import com.microsservice.msavaliadorcredito.controller.ex.ErroSolicitacao;
import com.microsservice.msavaliadorcredito.domain.*;
import com.microsservice.msavaliadorcredito.interfaces.CartoesResourceClient;
import com.microsservice.msavaliadorcredito.interfaces.ClienteResourceClient;
import com.microsservice.msavaliadorcredito.mqueue.SolicitacaoCartaoPublishe;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient cliente;
    private final CartoesResourceClient cartoes;

    private final SolicitacaoCartaoPublishe publisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteException, ErroComunicacaoException {
        try {
            ResponseEntity<DadosCliente> dadosClientes = cliente.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartoes = cartoes.cartoesCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClientes.getBody())
                    .cartoes(dadosCartoes.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteException();
            }
            throw new ErroComunicacaoException(e.getMessage(), status);
        }
    }

    public RetornoAvalicaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteException, ErroComunicacaoException{
       try{
           ResponseEntity<DadosCliente> dadosClientes = cliente.dadosCliente(cpf);
           ResponseEntity<List<Cartao>> cartoesResponse = cartoes.obterCartoesRendaAte(renda);

           List<Cartao> listaCartoes = cartoesResponse.getBody();

           List<CartaoAprovado> listaCartoesAprovados = listaCartoes.stream().map(cartao -> {
               return calcularRenda(renda, dadosClientes, cartao);
           }).collect(Collectors.toList());

           return new RetornoAvalicaoCliente(listaCartoesAprovados);

       }catch (FeignException.FeignClientException e) {
           int status = e.status();
           if (HttpStatus.NOT_FOUND.value() == status) {
               throw new DadosClienteException();
           }
           throw new ErroComunicacaoException(e.getMessage(), status);
       }
    }

    public Protocolo solicitarEmissaoCartao(DadosEmissaoCartao dados){
            try{
                publisher.solicitarCarto(dados);
                var protocolo = UUID.randomUUID().toString();
                return new Protocolo(protocolo);
            }catch (Exception e){
                throw  new ErroSolicitacao(e.getMessage());
            }
    }

    private static CartaoAprovado calcularRenda(Long renda, ResponseEntity<DadosCliente> dadosClientes, Cartao cartao) {
        DadosCliente dadosCliente = dadosClientes.getBody();
        BigDecimal limiteBasico = cartao.getLimiteBasico();
        BigDecimal rendaBd = BigDecimal.valueOf(renda);
        BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
        BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
        BigDecimal limiteAprovado = fator.multiply(limiteBasico);

        CartaoAprovado aprovado = new CartaoAprovado();
        aprovado.setCartao(cartao.getNome());
        aprovado.setBandeira(cartao.getBandeira());
        aprovado.setLimiteAprovado(limiteAprovado);

        return aprovado;
    }
}
