package com.microsservice.mscartoes.controllers;

import com.microsservice.mscartoes.dto.CartaoSaveRequest;
import com.microsservice.mscartoes.dto.ClienteCartaoResponse;
import com.microsservice.mscartoes.model.Cartao;
import com.microsservice.mscartoes.model.ClienteCartao;
import com.microsservice.mscartoes.servicies.CartaoService;
import com.microsservice.mscartoes.servicies.ClienteCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoResource {

    private final CartaoService service;

    private final ClienteCartaoService cartaoService;


    @GetMapping
    public String status(){
        return "OK";
    }

    @PostMapping
    public ResponseEntity<Cartao> cadastrar(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        service.save(cartao);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> obterCartoesRendaAte(@RequestParam Long renda){
        List<Cartao> cartaos = service.getcartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartaos);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoResponse>>cartoesCliente(@RequestParam String cpf){
        List<ClienteCartao> lista = cartaoService.listCartaoByCpf(cpf);
        List<ClienteCartaoResponse> result =
                lista
                .stream()
                .map(ClienteCartaoResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
