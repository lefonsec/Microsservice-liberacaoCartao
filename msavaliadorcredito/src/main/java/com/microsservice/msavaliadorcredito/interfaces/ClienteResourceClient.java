package com.microsservice.msavaliadorcredito.interfaces;

import com.microsservice.msavaliadorcredito.domain.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente>  dadosCliente(@RequestParam String cpf);

}
