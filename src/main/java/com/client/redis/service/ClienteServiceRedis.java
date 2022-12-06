package com.client.redis.service;

import com.client.redis.document.ClienteRedis;
import com.client.redis.dto.ClienteRequestDTO;
import com.client.redis.repository.ClienteRedisRepository;
import com.client.redis.utils.TextoUltils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClienteServiceRedis {

    @Autowired
    private ClienteRedisRepository clienteRedisRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ClienteRedis salvar(ClienteRequestDTO clienteRequestDTO) {
        ClienteRedis clienteRedis = convertClienteRedis(clienteRequestDTO);
        return clienteRedisRepository.save(clienteRedis);
    }

    public ClienteRedis convertClienteRedis(ClienteRequestDTO clienteRequestDTO) {
        clienteRequestDTO.setCpf(TextoUltils.removeEspecialCaracter(clienteRequestDTO.getCpf()));
        ClienteRedis clienteRedis = modelMapper.map(clienteRequestDTO, ClienteRedis.class);
        setNomeSobreNome(clienteRequestDTO, clienteRedis);
        return clienteRedis;
    }


    public void setNomeSobreNome(ClienteRequestDTO clienteRequestDTO, ClienteRedis clienteRedis) {
        int delimitadorIndex = clienteRequestDTO.getNomeCompleto().indexOf(" ");
        String nome = clienteRequestDTO.getNomeCompleto().substring(0, delimitadorIndex);
        String sobrenome = clienteRequestDTO.getNomeCompleto().substring(delimitadorIndex + 1, clienteRequestDTO.getNomeCompleto().length());

        clienteRedis.setNome(nome);
        clienteRedis.setSobrenome(sobrenome);
    }
}
