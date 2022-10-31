package com.microsservice.msclientes.cotroller;

import com.microsservice.msclientes.domain.Cliente;
import com.microsservice.msclientes.dto.ClienteSaveRequeste;
import com.microsservice.msclientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteService service;

    public String status (){
        log.info("Obtendo o status do microsservice de cliente");
        return "OK";
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody ClienteSaveRequeste request){
        Cliente cliente = request.toModel();
        service.save(cliente);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<Optional<Cliente>> dadosCliente(@RequestParam String cpf){
        Optional<Cliente> cliente = service.getByCpf(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

}
