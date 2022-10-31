package com.microsservice.msavaliadorcredito.controller;

import com.microsservice.msavaliadorcredito.controller.ex.DadosClienteException;
import com.microsservice.msavaliadorcredito.controller.ex.ErroComunicacaoException;
import com.microsservice.msavaliadorcredito.controller.ex.ErroSolicitacao;
import com.microsservice.msavaliadorcredito.domain.*;
import com.microsservice.msavaliadorcredito.interfaces.CartoesResourceClient;
import com.microsservice.msavaliadorcredito.servicies.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService service;

    @GetMapping(value = "situacao-clinete",params = "cpf")
    public ResponseEntity consultaStuacaoCliente(@RequestParam String cpf)  {
        try {
            SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvalicao dados){
        try {
            RetornoAvalicaoCliente avaliacao = service.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(avaliacao);
        } catch (DadosClienteException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@ RequestBody DadosEmissaoCartao dados){
        try{
            Protocolo protocolo = service.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocolo);
        }catch (ErroSolicitacao e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
