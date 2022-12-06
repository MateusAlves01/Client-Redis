package com.client.redis.controller;

import com.client.redis.document.ClienteRedis;
import com.client.redis.dto.ClienteRequestDTO;
import com.client.redis.dto.ClienteResponseDTO;
import com.client.redis.service.ClienteService;
import com.client.redis.service.ClienteServiceRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteServiceRedis clienteServiceRedis;

    @PostMapping
    public ClienteResponseDTO criarCliente(@Validated @RequestBody ClienteRequestDTO clienteRequestDTO){

        ClienteResponseDTO clienteSalvo = clienteService.criar(clienteRequestDTO);
        return clienteSalvo;

    }
    @PostMapping("async")
    public ClienteRedis criarClienteRedis(@Validated @RequestBody ClienteRequestDTO clienteRequestDTO) {

        ClienteRedis clienteSalvo = clienteServiceRedis.salvar(clienteRequestDTO);
        return clienteSalvo;
    }
    @PutMapping("async")
    public void sicronizarClienteBancoDados(){
        clienteService.sicronizarClienteBancoDados();
    }


    @GetMapping
    public List<ClienteResponseDTO> listarClientes(@RequestParam(required = false) String nome){
        return clienteService.listarClientes(nome);
    }

    @GetMapping("/{email}/email")
    public ClienteResponseDTO consultarPorEmail(@PathVariable String email){
        return clienteService.consultarPorEmail(email);
    }

    @GetMapping("/{cpf}/cpf")
    public ClienteResponseDTO consultarPorCpf( @PathVariable String cpf){
        return clienteService.consultarPorCpf(cpf);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(@PathVariable String email) throws Exception {
        clienteService.deletarCliente(email);
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCliente(@PathVariable String email, @Validated @RequestBody ClienteRequestDTO clienteRequestDTO) throws Exception {
        clienteService.atualizarCliente(clienteRequestDTO, email);
    }
}
